package com.example.authapp.Service;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.authapp.Api;
import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.Database.Repository.DetailJualRepository;
import com.example.authapp.Database.Repository.JualRepository;
import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.Model.ModelJual;
import com.example.authapp.Model.ModelOrder;
import com.example.authapp.Model.ModelPelanggan;
import com.example.authapp.Response.OrderResponse;
import com.example.authapp.SharedPref.SpHelper;
import com.example.authapp.ui.home.bottom_nav.shopping.TransactionSuccess;
import com.example.authapp.util.Modul;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderService {
    private ModelJual jual;
    private List<ModelDetailJual> detail;
    private List<ModelBarang> modelBarangs;
    private int idjual;
    private double total;
    private ModelPelanggan modelPelanggan;



    private static OrderService instance;

    public OrderService() {
        jual = new ModelJual("", 0, 0, 0, 0, 0, 24, "2022-05-17");
        detail = new ArrayList<>();
        modelBarangs = new ArrayList<>();
        idjual = 0;
        total = 0;
    }

    public void Bayar(double bayar){
        jual.setTotal(total);
        jual.setBayar(bayar);
    }

    public void save(Application application){
        jual.setTanggal_jual(Modul.getDate("yyyy-MM-dd HH:mm"));
        JualRepository jualRepository = new JualRepository(application);
        DetailJualRepository detailJualRepository = new DetailJualRepository(application);

        ModelOrder modelOrder = new ModelOrder(jual,detail);

        Call<OrderResponse> orderResponseCall = Api.Order(application.getApplicationContext()).postOrder(modelOrder);
        orderResponseCall.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()){
                    jualRepository.insert(response.body().getData().getJual(), new JualRepository.onInsertJual() {
                        @Override
                        public void onComplete(Long modelJual) {
                            detailJualRepository.insertAll(response.body().getData().getDetail(), modelJual);

                            Intent intent = new Intent(application, TransactionSuccess.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("kembali", Modul.doubleToStr(jual.getKembali()));
                            intent.putExtra("total", Modul.doubleToStr(jual.getTotal()));
                            intent.putExtra("idjual", modelJual.intValue());
                            application.startActivity(intent);
                            //Toast.makeText(application, "menyimpan", Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {

                }




                clearCart();
                LoadingDialog.close();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(application, t.getMessage(), Toast.LENGTH_SHORT).show();
                LoadingDialog.close();
            }
        });

    }

    //OrderService service = OrderService.getInstance(); //ini buat sekai pengecekan / konstruksi biar gk makan memori dn gk bakal di destroy
    //

    public int getJumlah(){
        int jumlah = 0;
        for(ModelDetailJual detailJual : detail){
            jumlah += detailJual.getJumlahjual();

        }
        return jumlah;
    }
    public static OrderService getInstance(){
        if (instance == null){
            instance = new OrderService();
        }
        return instance;
    }


    public int getIndexBarang(ModelBarang barang){
        int index =0;
        for (ModelBarang produk:
             modelBarangs) {
            if(produk.getIdbarang().equals(barang.getIdbarang())){
                return index;
            }
            index++;
        }
        return -1;
    }



    //buat nambah produk/pembelian
    public void add(ModelBarang modelBarang){
        if (getIndexBarang(modelBarang) == -1){
            //yg dibawah in buat mengetahui item ap sj yg akan dipanggil item
            modelBarangs.add(modelBarang);
            ModelDetailJual detailJual = new ModelDetailJual(idjual, modelBarang.getIdbarang(), 1, modelBarang.getHarga());
            detail.add(detailJual);
            total += modelBarang.getHarga()*1;

        } else {
            ModelDetailJual detailJual = detail.get(getIndexBarang(modelBarang));
            detailJual.addJumlah();
            detail.set(getIndexBarang(modelBarang), detailJual);
            total += detailJual.getHargajual();
            //total = modelBarang.getHarga() + total
            //total = total + modelBarang.getHarga() * jumlah jual
        }

    }

    //kurangi produk
    public void relieve(ModelBarang modelBarang){
        if(getIndexBarang(modelBarang)>=0){
            ModelDetailJual detailJual = detail.get(getIndexBarang(modelBarang));
            detailJual.relieveJumlah();
            detail.set(getIndexBarang(modelBarang), detailJual);
            total -= detailJual.getHargajual();
        }
    }

    public void setPelanggan(ModelPelanggan pelanggan){
        this.modelPelanggan = pelanggan;
        this.jual.setIdpelanggan(pelanggan.getIdpelanggan());
    }

    //ganti nama
    public ModelPelanggan getPelanggan(){
        if(this.modelPelanggan == null){
            ModelPelanggan pelanggan = new ModelPelanggan("Umum","","");
            return pelanggan;
        }
        return this.modelPelanggan;
    }

    //get detail buat cart
    public List<ModelDetailJual> getDetail(){
        return this.detail;
    }

    public void setJumlahBeli(ModelBarang modelBarang,int jumlahLama, int jumlahBeli,double hargaBaru){
        int posisi = getIndexBarang(modelBarang);
        if(posisi>=0) {
            if (jumlahBeli == 0) {
                modelBarangs.remove(posisi);
                detail.remove(posisi);
            } else {

                ModelDetailJual detailJual = detail.get(posisi);

                total -= modelBarang.getHarga() * jumlahLama;
                detailJual.setHargajual(hargaBaru);
                detailJual.setJumlahjual(jumlahBeli);
                detail.set(getIndexBarang(modelBarang), detailJual);
                total += detailJual.getHargajual() * jumlahBeli;
            }
        }
    }

    //get list barang buat ngecek jumlah atau counter
    public List<ModelBarang> getBarang(){
        return this.modelBarangs;

    }

    //buat detai jual
    public ModelDetailJual getDetailJual(ModelBarang modelBarang){
        if (getIndexBarang(modelBarang) == -1) {
            return null;
        }
        return detail.get(getIndexBarang(modelBarang));
    }

    public void clearCart(){
        modelBarangs.clear();
        detail.clear();
        total = 0;
    }

    public double getTotal(){
        return total;
    }
}

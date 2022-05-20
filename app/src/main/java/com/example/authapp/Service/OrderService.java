package com.example.authapp.Service;

import com.example.authapp.Model.ModelBarang;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.Model.ModelJual;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private ModelJual jual;
    private List<ModelDetailJual> detail;
    private List<ModelBarang> modelBarangs;
    private int idjual;
    private double total;


    private static OrderService instance;

    public OrderService() {
        jual = new ModelJual("", 0, 0, 0, 0, 0, 0, "2022-05-17");
        detail = new ArrayList<>();
        modelBarangs = new ArrayList<>();
        idjual = 0;
        total = 0;
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

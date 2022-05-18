package com.example.authapp.Service;

import android.graphics.ColorSpace;

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
    public static OrderService getInstance(){
        if (instance == null){
            instance = new OrderService();
        }
        return instance;
    }

    //buat nambah produk/pembelian
    public void add(ModelBarang modelBarang){
        if (modelBarangs.indexOf(modelBarang) == -1){
            //yg dibawah in buat mengetahui item ap sj yg akan dipanggil item
            modelBarangs.add(modelBarang);
            ModelDetailJual detailJual = new ModelDetailJual(idjual, modelBarang.getIdbarang(), 1, modelBarang.getHarga());
            detail.add(detailJual);
            total += modelBarang.getHarga()*1;

        } else {
            ModelDetailJual detailJual = detail.get(modelBarangs.indexOf(modelBarang));
            detailJual.addJumlah();
            detail.set(modelBarangs.indexOf(modelBarang), detailJual);
            total += modelBarang.getHarga();
            //tota; = modelBarang.getHarga() + total
            //tota; = total + modelBarang.getHarga() * jumlah jual
        }

    }

    //get list barang buat ngecek jumlah atau counter
    public List<ModelBarang> getBarang(){
        return this.modelBarangs;

    }
//buat detai jual
    public ModelDetailJual getDetailJual(ModelBarang modelBarang){
        if (modelBarangs.indexOf(modelBarang) == -1) {
            return null;
        }
        return detail.get(modelBarangs.indexOf(modelBarang));
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

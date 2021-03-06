 package com.example.authapp.ui.home.bottom_nav.shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Component.LoadingDialog;
import com.example.authapp.R;
import com.example.authapp.Service.OrderService;
import com.example.authapp.databinding.ActivityBayarBinding;
import com.example.authapp.util.Modul;

import java.text.NumberFormat;
import java.util.Locale;

 public class Payment extends AppCompatActivity {
    private ActivityBayarBinding bind;
    private TextView inBayar;
    private OrderService service = OrderService.getInstance();

    private String Bayar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bind = ActivityBayarBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(bind.getRoot());

        initTV();
        refreshData();
        Action();


    }

    public void initTV(){
        inBayar = bind.inBayar;
    }

    public void setBayar(String givenValue){
        Bayar = Bayar + givenValue;

        NumberFormat kurensi = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        inBayar.setText(kurensi.format(Modul.strToDouble(Bayar)));

        if (inBayar.getText() == "0"){
            bind.inBayar.setTextColor(getColor(R.color.darkgrey));
        } else {
            bind.inBayar.setTextColor(getColor(R.color.default1));
        }

        Double d = Modul.strToDouble(Bayar);
        if (d >= service.getTotal()) {
            bind.tvNext.setEnabled(true);
            bind.tvNext.setTextColor(getColor(R.color.default1));
        } else {
            bind.tvNext.setEnabled(false);
            bind.tvNext.setTextColor(getColor(R.color.darkgrey));
        }

    }

    public void refreshData(){
        bind.tvTotal.setText(Modul.removeE(service.getTotal()));
    }

    public void Del(View view){
        String recentValue = Bayar;
        if (recentValue.length() >= 2 ){
            bind.inBayar.setText(recentValue.substring(0, recentValue.length() - 1));
        } else {
            bind.inBayar.setText("0");
        }
    }


    public void Action(){
        bind.tcBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Payment.this, ShoppingCart.class));
                finish();
            }
        });

        bind.tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(Payment.this);
                alert.setTitle("Konfirmasi").setMessage("Apakah anda yakin untuk menyelesaikan transaksi ?").setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialog.load(Payment.this);
                        service.Bayar(Modul.strToDouble(Bayar));
                        service.save(getApplication());

                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });

        bind.clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.inBayar.setText("0");
                Bayar = "0";
            }
        });

        bind.shortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.manualPay1.setVisibility(View.GONE);
                bind.manualPay2.setVisibility(View.GONE);
                bind.manualPay3.setVisibility(View.GONE);
                bind.manualPay4.setVisibility(View.GONE);

                bind.quickPayRow1.setVisibility(View.VISIBLE);
                bind.quickPayRow2.setVisibility(View.VISIBLE);
                bind.quickPayRow3.setVisibility(View.VISIBLE);
            }
        });

        bind.txtManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bind.manualPay1.setVisibility(View.VISIBLE);
                bind.manualPay2.setVisibility(View.VISIBLE);
                bind.manualPay3.setVisibility(View.VISIBLE);
                bind.manualPay4.setVisibility(View.VISIBLE);

                bind.quickPayRow1.setVisibility(View.GONE);
                bind.quickPayRow2.setVisibility(View.GONE);
                bind.quickPayRow3.setVisibility(View.GONE);
            }
        });
    }

    public void txtSeven(View view){
        setBayar("7");
    }

    public void txtEight(View view){
        setBayar("8");
    }

    public void txtNine(View view){
        setBayar("9");
    }

    public void txtFour(View view){
        setBayar("4");
    }

    public void txtFive(View view){
        setBayar("5");
    }

    public void txtSix(View view){
        setBayar("6");
    }

    public void txtOne(View view){
        setBayar("1");
    }

    public void txtTwo(View view){
        setBayar("2");
    }

    public void txtThree(View view){
        setBayar("3");
    }

    public void txtZero(View view){
        setBayar("0");
    }

    public void txtTripleZero(View view){
        setBayar("000");
    }

    public void txtPoint(View view){
        setBayar(".");
    }

    public void uangPas(View view){
        Bayar = "0";
        setBayar(Modul.toString(service.getTotal()));
    }

    public void txtTwenty(View view){
        Bayar = "0";
        setBayar("20000");
    }

    public void txtFifty(View view){
        Bayar = "0";
        setBayar("50000");
    }

    public void txtHundret(View view){
        Bayar = "0";
        setBayar("100000");
    }

}

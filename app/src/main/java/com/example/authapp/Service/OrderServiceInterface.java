package com.example.authapp.Service;

import com.example.authapp.Model.ModelOrder;
import com.example.authapp.ModelView.ModelViewStruk;
import com.example.authapp.Response.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderServiceInterface {
    @POST("order")
    Call<OrderResponse> postOrder(@Body ModelOrder modelOrder);


    @GET("order/detail/{id}")
    Call<List<ModelViewStruk>> getOrderDetail(@Path("id") String id);
}

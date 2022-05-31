package com.example.authapp.Service;

import com.example.authapp.Model.ModelOrder;
import com.example.authapp.Response.OrderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderServiceInterface {
    @POST("order")
    Call<OrderResponse> postOrder(@Body ModelOrder modelOrder);
}

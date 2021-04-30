package edu.eci.ieti.myapplication.services;

import edu.eci.ieti.myapplication.model.LoginResponse;
import edu.eci.ieti.myapplication.model.RegisterWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("users")
    Call<Object> register(@Body RegisterWrapper registerWrapper);
}

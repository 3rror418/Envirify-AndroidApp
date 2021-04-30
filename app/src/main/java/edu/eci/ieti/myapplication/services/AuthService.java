package edu.eci.ieti.myapplication.services;

import edu.eci.ieti.myapplication.model.LoginWrapper;
import edu.eci.ieti.myapplication.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("users/login")
    Call<LoginResponse> login(@Body LoginWrapper loginWrapper);
}

package edu.eci.ieti.myapplication.services;

import edu.eci.ieti.myapplication.model.UserResponse;
import edu.eci.ieti.myapplication.model.UserWrapper;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{email}")
    Call<UserResponse> get(@Path("email") String email);

    @PUT("users")
    Call<Object> edit(@Body UserWrapper userWrapper);
}

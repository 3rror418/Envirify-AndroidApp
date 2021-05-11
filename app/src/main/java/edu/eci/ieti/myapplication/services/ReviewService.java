package edu.eci.ieti.myapplication.services;

import java.util.List;

import edu.eci.ieti.myapplication.model.Place;
import edu.eci.ieti.myapplication.model.ReviewCard;
import edu.eci.ieti.myapplication.model.ReviewWrapper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReviewService {
    @POST("ratings")
    Call<ResponseBody> addReview(@Query("placeId") String placeId, @Body ReviewWrapper review, @Header("X-Email") String email, @Header("Authorization") String token);

    @GET("ratings")
    Call<List<ReviewCard>> getRatings(@Query("placeId") String placeId);

}

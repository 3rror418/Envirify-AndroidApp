package edu.eci.ieti.myapplication.services;

import java.util.List;

import edu.eci.ieti.myapplication.model.Place;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PlaceService {
    @GET("places")
    Call<List<Place>> searchPlaces(@Query("search") String search);

    @POST("places")
    Call<ResponseBody> addPlace(@Body Place place, @Header("X-Email")String email);
}

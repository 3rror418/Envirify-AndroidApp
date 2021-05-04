package edu.eci.ieti.myapplication.services;

import java.util.List;

import edu.eci.ieti.myapplication.model.Book;
import edu.eci.ieti.myapplication.model.CreateBook;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookService {

    @GET("users/{email}/bookings")
    Call<List<Book>> searchMyBookings(@Path("email") String email);

    @POST("books")
    Call<ResponseBody> addBook(@Body CreateBook book, @Header("X-Email") String email, @Header("Authorization") String token);

    @GET("users/{email}/books")
    Call<List<Book>> bookingsToMe(@Path("email") String email);
}

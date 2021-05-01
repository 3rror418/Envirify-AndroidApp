package edu.eci.ieti.myapplication.services;

import java.util.List;

import edu.eci.ieti.myapplication.model.Book;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BookService {

    @GET("users/{email}/bookings")
    Call<List<Book>> searchMyBookings(@Path("email") String email);
}

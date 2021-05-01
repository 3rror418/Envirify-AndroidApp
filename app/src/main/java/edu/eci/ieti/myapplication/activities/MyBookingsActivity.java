package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.Book;
import edu.eci.ieti.myapplication.model.BookCard;
import edu.eci.ieti.myapplication.services.BookService;
import edu.eci.ieti.myapplication.ui.adapters.BookCardArrayAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBookingsActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private ListView listView;
    private BookCardArrayAdapter cardArrayAdapter;
    private BookService bookService;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);
        loadComponents();
        String email = sharedPref.getString(LoginActivity.USERNAME_EMAIL, "noexiste@gmail.com");
        executorService.execute(() -> {
            try {
                Response<List<Book>> response = bookService.searchMyBookings(email).execute();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        List<Book> books = response.body();
                        listView = (ListView) findViewById(R.id.bookcard_listView);
                        listView.addHeaderView(new View(this));
                        listView.addFooterView(new View(this));
                        cardArrayAdapter = new BookCardArrayAdapter(getApplicationContext(), R.layout.list_book_card);
                        if (books != null) {
                            System.out.println(books);
                            for (Book book : books) {
                                cardArrayAdapter.add(new BookCard(book));
                            }
                        }
                        listView.setAdapter(cardArrayAdapter);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Error de Conexion con BackEnd", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadComponents() {
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bookService = retrofit.create(BookService.class);
    }
}
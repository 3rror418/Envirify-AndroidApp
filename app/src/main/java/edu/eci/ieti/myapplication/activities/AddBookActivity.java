package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.CreateBook;
import edu.eci.ieti.myapplication.services.BookService;
import edu.eci.ieti.myapplication.ui.fragments.DatePickerFragment;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBookActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private EditText editInitialDate;
    private EditText editFinalDate;
    private SharedPreferences sharedPreferences;
    private BookService bookService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        editInitialDate = findViewById(R.id.editInitialDate);
        editInitialDate.setOnClickListener(this::onClick);
        editFinalDate = findViewById(R.id.editFinalDate);
        editFinalDate.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editInitialDate:
                showInitialDatePickerDialog();
                break;
            case R.id.editFinalDate:
                showFinalDatePickerDialog();
                break;
        }
    }

    public void makeBook(View view) {
        loadComponents();
        String userToken = sharedPreferences.getString(LaunchActivity.TOKEN_KEY, "");
        String placeId = sharedPreferences.getString(SearchActivity.SELECTED_PLACE_ID, "");
        String email = sharedPreferences.getString(LoginActivity.USERNAME_EMAIL, "");
        String initialDate = editInitialDate.getText().toString();
        String finalDate = editFinalDate.getText().toString();
        if (initialDate.isEmpty() || finalDate.isEmpty() || placeId.isEmpty() || email.isEmpty() || userToken.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Algunos campos siguen vacios", Toast.LENGTH_LONG);
            toast.show();
        } else {
            CreateBook newBook = new CreateBook(initialDate, finalDate, placeId);
            executorService.execute(() -> {
                try {
                    System.out.println(newBook);
                    Response<ResponseBody> response = bookService.addBook(newBook, email, "Bearer " + userToken).execute();
                    runOnUiThread(() -> {
                        Toast toast;
                        String message;
                        if (response.isSuccessful()) {
                            message = "Reserva Creada";
                        } else {
                            System.out.println(response);
                            message = response.message();
                        }
                        System.out.println(message);
                        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                        toast.show();
                    });
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error de Conexion con el BackEnd", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }
    }

    private void showInitialDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            final String selectedDate = twoDigits(year) + "-" + twoDigits(month + 1) + "-" + twoDigits(day); // +1 because January is zero
            //System.out.println(selectedDate);
            editInitialDate.setText(selectedDate);
        });
        newFragment.show(getSupportFragmentManager(), "initialDatePicker");
    }

    private void showFinalDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            final String selectedDate = twoDigits(year) + "-" + twoDigits(month + 1) + "-" + twoDigits(day); // +1 because January is zero
            //System.out.println(selectedDate);
            editFinalDate.setText(selectedDate);
        });
        newFragment.show(getSupportFragmentManager(), "finalDatePicker");
    }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

    private void loadComponents() {
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        bookService = retrofit.create(BookService.class);
    }
}
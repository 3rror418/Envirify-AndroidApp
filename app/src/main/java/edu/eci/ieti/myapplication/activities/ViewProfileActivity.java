package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.UserResponse;
import edu.eci.ieti.myapplication.model.UserWrapper;
import edu.eci.ieti.myapplication.services.UserService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewProfileActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private SharedPreferences sharedPreferences;

    private UserService userService;

    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewGenre;
    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        loadComponents();

        String email = sharedPreferences.getString(LoginActivity.USERNAME_EMAIL, "noexiste@gmail.com");

        textViewEmail = findViewById(R.id.textViewEmail);
        textViewEmail.setText(email);
        textViewGenre = findViewById(R.id.textViewGenre);
        textViewName = findViewById(R.id.textViewName);
        textViewPhone = findViewById(R.id.textViewPhone);

        executorService.execute(() -> {
            try {
                Response<UserResponse> response = userService.get(email).execute();
                UserResponse userResponse = response.body();
                System.out.println(response.body());
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        textViewGenre.setText(userResponse.getGender());
                        textViewName.setText(userResponse.getName());
                        textViewPhone.setText(userResponse.getPhoneNumber());
                    } else {
                        textViewGenre.setError("Error");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void loadComponents() {
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //localhost for emulator
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }
}
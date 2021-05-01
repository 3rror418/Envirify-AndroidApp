package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.LoginWrapper;
import edu.eci.ieti.myapplication.model.LoginResponse;
import edu.eci.ieti.myapplication.services.AuthService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME_ID = "USERNAME_ID";
    public static final String USERNAME_EMAIL = "USERNAME_EMAIL";


    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private EditText editTextEmail;
    private EditText editTextPassword;
    private SharedPreferences sharedPreferences;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Called When The User Taps The Login Button
     */
    public void makeLogin(View view) {
        loadComponents();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (email.isEmpty()) editTextEmail.setError("This field can not be blank");
        if (password.isEmpty()) editTextPassword.setError("This field can not be blank");
        if (!email.isEmpty() && !password.isEmpty()) {
            executorService.execute(() -> {
                try {
                    Response<LoginResponse> response =
                            authService.login(new LoginWrapper(email, password)).execute();
                    LoginResponse loginResponse = response.body();
                    runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(LaunchActivity.TOKEN_KEY, loginResponse.getJwt());
                            editor.putString(USERNAME_EMAIL, loginResponse.getEmail());
                            editor.putString(USERNAME_ID, loginResponse.getId());
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            editTextEmail.setText("");
                            editTextPassword.setText("");
                        } else {
                            editTextPassword.setError("Invalid Credentials");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Called When The User Taps The Login Button
     */
    public void redirectToRegister(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityRegister.class);
        startActivity(intent);
    }

    private void loadComponents() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        //localhost for emulator
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authService = retrofit.create(AuthService.class);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }
}
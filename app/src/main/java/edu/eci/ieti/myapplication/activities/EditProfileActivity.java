package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

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

public class EditProfileActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private EditText editTextTextEmailAddress;
    private EditText editTextPhone;
    private EditText editTextTextPersonGenre;
    private EditText editTextTextPersonName;
    private UserService userService;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }

    public void editProfile(){
        loadComponents();
        String email = sharedPreferences.getString(LoginActivity.USERNAME_EMAIL, "noexiste@gmail.com");
        String name = "NA";
        String genre = "NA";
        String phone = "NA";

        if (email.isEmpty()) editTextTextEmailAddress.setError("This field can not be blank");
        if (!email.isEmpty()) {
            executorService.execute(() -> {
                try {
                    Response<Object> response = userService.edit(new UserWrapper(email, name, phone, genre)).execute();
                    UserResponse userResponse = (UserResponse) response.body();
                    runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            editTextTextEmailAddress.setText("");
                            editTextPhone.setText("");
                            editTextTextPersonGenre.setText("");
                            editTextTextPersonName.setText("");
                        } else {
                            editTextTextPersonName.setError("Error");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private void loadComponents() {
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPersonGenre = findViewById(R.id.editTextTextPersonGenre);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextPhone = findViewById(R.id.editTextPhone);
        //localhost for emulator
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
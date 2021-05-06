package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        loadComponents();
    }

    public void editProfile(View view){
        String email = sharedPreferences.getString(LoginActivity.USERNAME_EMAIL, "noexiste@gmail.com");
        String gender = findViewById(R.id.editTextTextPersonGenre).toString();
        String name = findViewById(R.id.editTextTextPersonName).toString();
        String phone = findViewById(R.id.editTextPhone).toString();
        String password = sharedPreferences.getString(ViewProfileActivity.USERNAME_PASSWORD, "NA");

        executorService.execute(() -> {
            try {
                Response<UserResponse> response = userService.get(email).execute();
                UserResponse userResponse = response.body();
                System.out.println(response.body());
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        Set<String> books = new HashSet<>();
                        books.addAll((Collection<? extends String>) sharedPreferences);
                        editor.putStringSet("booksList", books);

                        Set<String> chats = new HashSet<>();
                        chats.addAll((Collection<? extends String>) sharedPreferences);
                        editor.putStringSet("chatsList", books);

                        Set<String> places = new HashSet<>();
                        places.addAll((Collection<? extends String>) sharedPreferences);
                        editor.putStringSet("placesList", books);

                    } else {
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<String> books = (List<String>) sharedPreferences.getStringSet("booksList", null);
        List<String> chats = (List<String>) sharedPreferences.getStringSet("chatsList", null);
        List<String> places = (List<String>) sharedPreferences.getStringSet("placesList", null);

        if (email.isEmpty()) editTextTextEmailAddress.setError("This field can not be blank");
        if (!email.isEmpty()) {
            executorService.execute(() -> {
                try {
                    Response<UserWrapper> response = userService.edit(new UserWrapper(email, name, phone, gender, password, chats, books, places)).execute();
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
        editTextTextEmailAddress = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextTextPersonGenre = findViewById(R.id.editTextTextPersonGenre);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //localhost for emulator
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userService = retrofit.create(UserService.class);
    }
}
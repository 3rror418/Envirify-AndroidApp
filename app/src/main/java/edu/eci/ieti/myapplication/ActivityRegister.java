package edu.eci.ieti.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.model.RegisterWrapper;
import edu.eci.ieti.myapplication.services.RegisterService;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityRegister extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private TextView editRegisterName;
    private TextView editRegisterPassword;
    private TextView editRegisterEmail;
    private TextView editRegisterNumber;
    private TextView editRegisterGenre;
    private TextView editRegisterConfirmPassword;

    private RegisterService registerService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void makeRegister(View view) {
        loadComponents();
        String name = editRegisterName.getText().toString();
        String email = editRegisterEmail.getText().toString();
        String genre = editRegisterGenre.getText().toString();
        String number = editRegisterNumber.getText().toString();
        String password = editRegisterPassword.getText().toString();
        String confirmPassword = editRegisterConfirmPassword.getText().toString();

        boolean error = false;
        if (email.isEmpty()){
            editRegisterEmail.setError("This field can not be blank");
            error = true;
        }
        if (password.isEmpty()){
            editRegisterPassword.setError("This field can not be blank");
            error = true;
        }
        if (name.isEmpty()){
            editRegisterEmail.setError("This field can not be blank");
            error = true;
        }
        if (genre.isEmpty()){
            editRegisterEmail.setError("This field can not be blank");
            error = true;
        }
        if (number.isEmpty()){
            editRegisterEmail.setError("This field can not be blank");
            error = true;
        }
        if (confirmPassword.isEmpty()){
            editRegisterEmail.setError("This field can not be blank");
            error = true;
        }

        if(!password.equals(confirmPassword)){
            editRegisterPassword.setError("Passwords do not match");
            editRegisterConfirmPassword.setError("Passwords do not match");
            error = true;
        }

        if( !( genre.equals("Male") || genre.equals("Female") || genre.equals("Other") ) ){
            editRegisterGenre.setError("Genre has to be Male , Female or Other");
            error = true;
        }

        if (!error) {
            executorService.execute(() -> {
                try {
                    Response<Object> response =
                            registerService.register(new RegisterWrapper(email,name,number,genre,password)).execute();
                    runOnUiThread(() -> {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void loadComponents() {
        editRegisterName = findViewById(R.id.registerName);
        editRegisterEmail = findViewById(R.id.registerEmail);
        editRegisterNumber = findViewById(R.id.registerNumber);
        editRegisterPassword = findViewById(R.id.registerPassword);
        editRegisterGenre = findViewById(R.id.registerGenre);
        editRegisterConfirmPassword = findViewById(R.id.registerConfirmPassword);

        //localhost for emulator
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/") //localhost for emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        registerService = retrofit.create(RegisterService.class);
    }
}
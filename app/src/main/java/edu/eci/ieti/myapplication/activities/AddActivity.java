package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.Place;
import edu.eci.ieti.myapplication.services.PlaceService;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextDepartment;
    private EditText editTextCity;
    private EditText editTextDirection;
    private EditText editTextDescription;
    private EditText editTextCapacity;
    private EditText editTextHabitations;
    private EditText editTextBathrooms;
    private PlaceService placeService;
    private SharedPreferences sharedPref;

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onSubmitAdd(View view) {
        loadComponents();
        String name = editTextName.getText().toString();
        String department = editTextDepartment.getText().toString();
        String city = editTextCity.getText().toString();
        String direction = editTextDirection.getText().toString();
        String description = editTextDescription.getText().toString();
        String urlImg = "https://i.blogs.es/8e8f64/lo-de-que-comprar-una-casa-es-la-mejor-inversion-hay-generaciones-que-ya-no-lo-ven-ni-de-lejos---1/450_1000.jpg";
        int capacity = Integer.parseInt(editTextCapacity.getText().toString());
        int habitations = Integer.parseInt(editTextHabitations.getText().toString());
        int bathrooms = Integer.parseInt(editTextBathrooms.getText().toString());

        if (name.isEmpty()) {
            editTextName.setError("El parametro de nombre no puede estar vacio");
        } else {
            String email = sharedPref.getString(LoginActivity.USERNAME_EMAIL, "noexiste@gmail.com");
            executorService.execute(() -> {
                try {
                    Response<ResponseBody> response =
                            placeService.addPlace(new Place(name, department, city, direction, description, urlImg, capacity, habitations, bathrooms), email).execute();
                    runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            System.out.println("suuu");
                        } else {
                            editTextName.setError("Error de Conexion con BackEnd");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private void loadComponents() {
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        placeService = retrofit.create(PlaceService.class);
        editTextName = findViewById(R.id.AddName);
        editTextDepartment = findViewById(R.id.AddDepartment);
        editTextCity = findViewById(R.id.AddCity);
        editTextDirection = findViewById(R.id.AddDirection);
        editTextDescription = findViewById(R.id.AddDescription);
        editTextCapacity = findViewById(R.id.AddCapacity);
        editTextHabitations = findViewById(R.id.AddHabitations);
        editTextBathrooms = findViewById(R.id.AddBathrooms);

    }
}
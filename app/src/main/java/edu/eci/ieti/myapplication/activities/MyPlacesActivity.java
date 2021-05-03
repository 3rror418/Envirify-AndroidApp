package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.Card;
import edu.eci.ieti.myapplication.model.Place;
import edu.eci.ieti.myapplication.services.PlaceService;
import edu.eci.ieti.myapplication.ui.adapters.CardArrayAdapter;
import edu.eci.ieti.myapplication.ui.adapters.CardPlaceArrayAdapter;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyPlacesActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private PlaceService placeService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private ListView listView;
    private CardPlaceArrayAdapter cardArrayAdapter;
    private AlertDialog.Builder builder;
    private Button deleteButton;

    public static final String SELECTED_PLACE_ID = "SELECTED_PLACE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);
        loadComponents();
        String email = sharedPreferences.getString(LoginActivity.USERNAME_EMAIL, "noexiste@gmail.com");
        executorService.execute(() -> {
            try {
                Response<List<Place>> response = placeService.myPlaces(email).execute();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        List<Place> places = response.body();
                        listView = findViewById(R.id.card_listView);
                        listView.addHeaderView(new View(this));
                        listView.addFooterView(new View(this));
                        cardArrayAdapter = new CardPlaceArrayAdapter(getApplicationContext(), R.layout.list_item_card_place, this);
                        if (places != null) {
                            System.out.println(places);
                            for (Place place : places) {
                                cardArrayAdapter.add(new Card(place));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void loadComponents() {
        builder = new AlertDialog.Builder(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        placeService = retrofit.create(PlaceService.class);
    }

    public void putItemSelectedId(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.contains(SELECTED_PLACE_ID)) {
            editor.remove(SELECTED_PLACE_ID);
        }
        editor.putString(SELECTED_PLACE_ID, id);
        editor.apply();
    }

    public void deletePlace() {
        System.out.println("Hice click en delete");
        builder.setTitle("Confirmación de Eliminación");
        builder.setMessage("¿Estas seguro de eliminar la información de este lugar, toda la información se perdera?");
        builder.setPositiveButton("Aceptar", this::deletePlaceById);
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deletePlaceById(DialogInterface dialog, int which) {
        String placeId = sharedPreferences.getString(SELECTED_PLACE_ID, "");
        String email = sharedPreferences.getString(LoginActivity.USERNAME_EMAIL, "");
        if (placeId.isEmpty() || email.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Intentelo mas tarde", Toast.LENGTH_LONG);
            toast.show();
        } else {
            executorService.execute(() -> {
                try {
                    Response<ResponseBody> response = placeService.deletePlace(placeId, email).execute();
                    runOnUiThread(() -> {
                        Toast toast;
                        String message;
                        if (response.isSuccessful()) {
                            message = "Hospedaje Eliminado";
                        } else {
                            System.out.println(response);
                            message = response.message();
                        }
                        System.out.println(message);
                        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                        toast.show();
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
package edu.eci.ieti.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.model.Card;
import edu.eci.ieti.myapplication.model.Place;
import edu.eci.ieti.myapplication.services.PlaceService;
import edu.eci.ieti.myapplication.ui.CardArrayAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private EditText editTextPlace;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private PlaceService placeService;
    private ListView listView;
    private CardArrayAdapter cardArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void Search(View view) {
        loadComponents();
        String search = editTextPlace.getText().toString();
        if (search.isEmpty()) {
            editTextPlace.setError("El parametro de busqueda no puede estar vacio");
        } else {
            executorService.execute(() -> {
                try {
                    Response<List<Place>> response =
                            placeService.searchPlaces(search).execute();
                    runOnUiThread(() -> {
                        if (response.isSuccessful()) {
                            List<Place> places = response.body();
                            listView = (ListView) findViewById(R.id.card_listView);
                            listView.addHeaderView(new View(this));
                            listView.addFooterView(new View(this));
                            cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
                            if (places != null) {
                                System.out.println(places);
                                for (Place place : places) {
                                    cardArrayAdapter.add(new Card(place));
                                }
                            }
                            listView.setAdapter(cardArrayAdapter);
                        } else {
                            editTextPlace.setError("Error de Conexion con BackEnd");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void loadComponents() {
        editTextPlace = findViewById(R.id.searchText);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        placeService = retrofit.create(PlaceService.class);
    }
}
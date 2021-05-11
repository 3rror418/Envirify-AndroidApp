package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.ReviewCard;
import edu.eci.ieti.myapplication.services.ReviewService;
import edu.eci.ieti.myapplication.ui.adapters.ReviewCardArrayAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewReviewsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ReviewService reviewService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private ListView listView;
    private ReviewCardArrayAdapter cardArrayAdapter;
    private AlertDialog.Builder builder;
    private Button deleteButton;

    public static final String SELECTED_PLACE_ID = "SELECTED_PLACE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        loadComponents();
        String email = sharedPreferences.getString(LoginActivity.USERNAME_EMAIL, "noexiste@gmail.com");
        String placeId = sharedPreferences.getString(SearchActivity.SELECTED_PLACE_ID, "");
        RatingBar generalScore = findViewById(R.id.review_general_score);

        executorService.execute(() -> {
            try {
                Response<List<ReviewCard>> response = reviewService.getRatings(placeId).execute();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        List<ReviewCard> reviews = response.body();
                        listView = findViewById(R.id.reviewCard_listView);
                        listView.addHeaderView(new View(this));
                        listView.addFooterView(new View(this));
                        cardArrayAdapter = new ReviewCardArrayAdapter(getApplicationContext(), R.layout.list_review_card, this);
                        if (reviews != null) {
                            System.out.println(reviews);
                            int score = 0;
                            for (ReviewCard reviewCard : reviews) {
                                score+= reviewCard.getQualification();
                                cardArrayAdapter.add(reviewCard);
                            }
                            score = score / reviews.size();
                            generalScore.setRating(score);
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
        reviewService = retrofit.create(ReviewService.class);
    }

}
package edu.eci.ieti.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.ReviewWrapper;
import edu.eci.ieti.myapplication.services.ReviewService;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddReviewActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newFixedThreadPool(1);
    private EditText editDescription;
    private RatingBar editRating;
    private SharedPreferences sharedPreferences;
    private ReviewService reviewService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
    }


    public void addReview(View view) {
        loadComponents();
        String userToken = sharedPreferences.getString(LaunchActivity.TOKEN_KEY, "");
        String placeId = sharedPreferences.getString(SearchActivity.SELECTED_PLACE_ID, "");
        String email = sharedPreferences.getString(LoginActivity.USERNAME_EMAIL, "");
        int qualification = (int) editRating.getRating();
        System.out.println(qualification);
        String description = editDescription.getText().toString();
        if (description.isEmpty() || qualification==0 || placeId.isEmpty() || email.isEmpty() || userToken.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Some fields are empty", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            ReviewWrapper review = new ReviewWrapper(description, qualification, email);
            executorService.execute(() -> {
                try {
                    Response<ResponseBody> response = reviewService.addReview(placeId, review, email, "Bearer " + userToken).execute();
                    runOnUiThread(() -> {
                        Toast toast;
                        String message;
                        if (response.isSuccessful()) {
                            message = "Review created!";
                        } else {
                            System.out.println(response);
                            message = response.message();
                        }
                        System.out.println(message);
                        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
                        toast.show();
                    });
                } catch (IOException e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Connection error with the back end", Toast.LENGTH_LONG);
                    toast.show();
                }
            });
        }
    }


    private void loadComponents() {
        editDescription = findViewById(R.id.add_review_description);
        editRating = findViewById(R.id.add_review_rating);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://enfiry-back-end.herokuapp.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        reviewService = retrofit.create(ReviewService.class);
    }


}
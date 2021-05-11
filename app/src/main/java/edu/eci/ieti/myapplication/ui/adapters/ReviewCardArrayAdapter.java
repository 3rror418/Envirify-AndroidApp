package edu.eci.ieti.myapplication.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.activities.ViewReviewsActivity;
import edu.eci.ieti.myapplication.model.ReviewCard;

public class ReviewCardArrayAdapter extends ArrayAdapter<ReviewCard> {

    private final List<ReviewCard> cardList = new ArrayList<>();
    private ViewReviewsActivity viewReviewsActivity;

    static class CardViewHolder {
        RatingBar rating;
        TextView comment;
        TextView name;
    }

    public ReviewCardArrayAdapter(Context context, int textViewResourceId, ViewReviewsActivity viewReviewsActivity) {
        super(context, textViewResourceId);
        this.viewReviewsActivity = viewReviewsActivity ;
    }


    @Override
    public void add(ReviewCard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public ReviewCard getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_review_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.name = row.findViewById(R.id.view_review_name);
            viewHolder.comment = row.findViewById(R.id.view_review_comment);
            viewHolder.rating = row.findViewById(R.id.view_reviews_rating);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        ReviewCard card = getItem(position);
        viewHolder.name.setText(card.getOwner());
        viewHolder.rating.setNumStars(5);
        viewHolder.rating.setRating(card.getQualification());
        viewHolder.comment.setText(card.getComment());
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}
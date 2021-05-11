package edu.eci.ieti.myapplication.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.activities.AddBookActivity;
import edu.eci.ieti.myapplication.activities.AddReviewActivity;
import edu.eci.ieti.myapplication.activities.SearchActivity;
import edu.eci.ieti.myapplication.activities.ViewReviewsActivity;
import edu.eci.ieti.myapplication.model.Card;

public class CardArrayAdapter extends ArrayAdapter<Card> {

    private final List<Card> cardList = new ArrayList<>();
    private SearchActivity searchActivity;

    static class CardViewHolder {
        ImageView image;
        TextView name;
        TextView address; // department+city
        RatingBar calificacion;
        TextView description;
        TextView propietario;
        Button bookButton;
        Button reviewButton;
        Button viewReviewButton;
    }

    public CardArrayAdapter(Context context, int textViewResourceId, SearchActivity searchActivity) {
        super(context, textViewResourceId);
        this.searchActivity = searchActivity;
    }


    @Override
    public void add(Card object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Card getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.image = row.findViewById(R.id.place_image);
            viewHolder.name = row.findViewById(R.id.place_name);
            viewHolder.address = row.findViewById(R.id.place_address);
            viewHolder.description = row.findViewById(R.id.place_description);
            viewHolder.propietario = row.findViewById(R.id.place_owner);
            viewHolder.bookButton = row.findViewById(R.id.makebookButton);
            viewHolder.reviewButton = row.findViewById(R.id.add_review_button);
            viewHolder.viewReviewButton = row.findViewById(R.id.view_reviews_button);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        Card card = getItem(position);
        Picasso.get().load(card.getUrlImage()).into(viewHolder.image);
        viewHolder.name.setText(card.getName());
        viewHolder.address.setText(String.format("%s, %s", card.getCity(), card.getDepartment()));
        viewHolder.description.setText(card.getDescription());
        viewHolder.propietario.setText(card.getOwner());
        viewHolder.bookButton.setOnClickListener(onClickListener -> {
            searchActivity.putItemSelectedId(card.getId());
            Intent intent = new Intent(searchActivity.getApplicationContext(), AddBookActivity.class);
            searchActivity.startActivity(intent);
        });

        viewHolder.reviewButton.setOnClickListener(onClickListener -> {
            searchActivity.putItemSelectedId(card.getId());
            Intent intent = new Intent(searchActivity.getApplicationContext(), AddReviewActivity.class);
            searchActivity.startActivity(intent);
        });
        viewHolder.viewReviewButton.setOnClickListener(onClickListener -> {
            searchActivity.putItemSelectedId(card.getId());
            Intent intent = new Intent(searchActivity.getApplicationContext(), ViewReviewsActivity.class);
            searchActivity.startActivity(intent);
        });
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}
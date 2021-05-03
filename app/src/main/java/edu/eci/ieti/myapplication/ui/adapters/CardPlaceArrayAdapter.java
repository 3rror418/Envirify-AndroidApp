package edu.eci.ieti.myapplication.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.activities.MyPlacesActivity;
import edu.eci.ieti.myapplication.model.Card;

public class CardPlaceArrayAdapter extends ArrayAdapter<Card> {

    private final List<Card> cardList = new ArrayList<>();
    private MyPlacesActivity myPlaces;

    static class CardPlaceViewHolder {
        ImageView image;
        TextView name;
        TextView address; // department+city
        TextView description;
        TextView propietario;
        Button deleteButton;
    }

    public CardPlaceArrayAdapter(Context context, int textViewResourceId, MyPlacesActivity myPlaces) {
        super(context, textViewResourceId);
        this.myPlaces = myPlaces;
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
        CardPlaceArrayAdapter.CardPlaceViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card_place, parent, false);
            viewHolder = new CardPlaceArrayAdapter.CardPlaceViewHolder();
            viewHolder.image = row.findViewById(R.id.place_image);
            viewHolder.name = row.findViewById(R.id.place_name);
            viewHolder.address = row.findViewById(R.id.place_address);
            viewHolder.description = row.findViewById(R.id.place_description);
            viewHolder.propietario = row.findViewById(R.id.place_owner);
            viewHolder.deleteButton = row.findViewById(R.id.deletebutton);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardPlaceArrayAdapter.CardPlaceViewHolder) row.getTag();
        }
        Card card = getItem(position);
        Picasso.get().load(card.getUrlImage()).into(viewHolder.image);
        viewHolder.name.setText(card.getName());
        viewHolder.address.setText(String.format("%s, %s", card.getCity(), card.getDepartment()));
        viewHolder.description.setText(card.getDescription());
        viewHolder.propietario.setText(card.getOwner());
        viewHolder.deleteButton.setOnClickListener(onClickListener -> {
            myPlaces.putItemSelectedId(card.getId());
            myPlaces.deletePlace();
        });
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }



}

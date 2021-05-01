package edu.eci.ieti.myapplication.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.eci.ieti.myapplication.R;
import edu.eci.ieti.myapplication.model.BookCard;

public class BookCardArrayAdapter extends ArrayAdapter<BookCard> {

    private final List<BookCard> cardList = new ArrayList<>();

    private final SimpleDateFormat sdf;

    static class CardViewHolder {
        ImageView image;
        TextView name;
        TextView address; // department+city
        TextView initialDate;
        TextView finalDate;
    }

    public BookCardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    }

    @Override
    public void add(BookCard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public BookCard getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BookCardArrayAdapter.CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_book_card, parent, false);
            viewHolder = new BookCardArrayAdapter.CardViewHolder();
            viewHolder.image = row.findViewById(R.id.book_image);
            viewHolder.name = row.findViewById(R.id.book_name);
            viewHolder.address = row.findViewById(R.id.book_address);
            viewHolder.initialDate = row.findViewById(R.id.book_initialDate);
            viewHolder.finalDate = row.findViewById(R.id.book_finalDate);
            row.setTag(viewHolder);
        } else {
            viewHolder = (BookCardArrayAdapter.CardViewHolder) row.getTag();
        }
        BookCard card = getItem(position);
        Picasso.get().load(card.getPlace().getUrlImage()).into(viewHolder.image);
        viewHolder.name.setText(card.getPlace().getName());
        viewHolder.address.setText(String.format("%s, %s", card.getPlace().getCity(), card.getPlace().getDepartment()));
        viewHolder.initialDate.setText(sdf.format(card.getInitialDate()));
        viewHolder.finalDate.setText(sdf.format(card.getFinalDate()));
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}

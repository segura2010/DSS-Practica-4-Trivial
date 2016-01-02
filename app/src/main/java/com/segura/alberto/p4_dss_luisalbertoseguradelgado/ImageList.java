package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alberto on 2/1/16.
 */

public class ImageList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] text;
    private final Integer[] images;

    public ImageList(Activity context, String[] text, Integer[] images) {
        super(context, R.layout.image_list, text);
        this.context = context;

        this.text = text;
        this.images = images;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.image_list, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        //txtTitle.setText(text[position]);

        imageView.setImageResource(images[position]);
        return rowView;
    }
}

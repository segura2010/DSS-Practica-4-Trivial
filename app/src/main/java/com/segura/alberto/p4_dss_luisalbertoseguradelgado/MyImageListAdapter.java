package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alberto on 8/1/16.
 */
public class MyImageListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] titles;
    private final String[] descs;
    private final Integer[] images;

    public MyImageListAdapter(Activity context, String[] titles, String[] descs, Integer[] images) {
        super(context, R.layout.my_imagelist, titles);
        this.context = context;

        this.titles = titles;
        this.descs = descs;
        this.images = images;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.my_imagelist, null, true);

        TextView titleTxt = (TextView) rowView.findViewById(R.id.itemTitle);
        TextView descTxt = (TextView) rowView.findViewById(R.id.itemDescription);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.itemImage);

        titleTxt.setText(titles[position]);
        descTxt.setText(descs[position]);
        imageView.setImageResource(images[position]);

        return rowView;
    }
}

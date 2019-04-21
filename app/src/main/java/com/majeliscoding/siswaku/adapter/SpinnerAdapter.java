package com.majeliscoding.siswaku.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.majeliscoding.siswaku.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    private Context context;




    public SpinnerAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public SpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    public SpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    public SpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView tv = (TextView) view;
        Typeface typeface = ResourcesCompat.getFont(parent.getContext(), R.font.raleway_regular);
        tv.setTypeface(typeface, Typeface.NORMAL);

        if(position == 0){
            tv.setTextColor(parent.getContext().getResources().getColor(R.color.lightGray));
        }else{
            tv.setTextColor(Color.BLACK);
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        Typeface typeface = ResourcesCompat.getFont(parent.getContext(), R.font.raleway_regular);
        tv.setTypeface(typeface, Typeface.NORMAL);

        if(position == 0){
            tv.setTextColor(Color.GRAY);
        }else{
            tv.setTextColor(Color.BLACK);
        }

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }
}

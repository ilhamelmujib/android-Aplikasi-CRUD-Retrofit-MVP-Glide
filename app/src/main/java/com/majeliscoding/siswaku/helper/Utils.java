package com.majeliscoding.siswaku.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.LinearLayout;


import com.majeliscoding.siswaku.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Dialog progressDialog(Context context){
        Dialog modals = new Dialog(context);
        modals.requestWindowFeature(Window.FEATURE_NO_TITLE);
        modals.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modals.setContentView(R.layout.modals_progress_dialog);
        modals.setCanceledOnTouchOutside(false);
        //modals.setCancelable(false);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        modals.getWindow().setLayout((7 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);
        return modals;
    }

    public static String formatDate(String from, String to, String date) {
        try {
            Date dob = (new SimpleDateFormat(from)).parse(date);
            return (new SimpleDateFormat(to)).format(dob);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


}

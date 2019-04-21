package com.majeliscoding.siswaku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.majeliscoding.siswaku.R;
import com.majeliscoding.siswaku.activity.student.StudentActivity;
import com.majeliscoding.siswaku.helper.DataConfig;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        DataConfig.storeString(this, DataConfig.TOKEN, "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjhjNzJmN2VkNTM1MjRiN2JiZjQyMmIzNDU1Mzc5M2IzZmI2OGI4MDJlZTM2NGYwNTc5M2ZkMTg2YzlhNjJmY2FkMmY5ZDA1MjRiMzYyYjBkIn0.eyJhdWQiOiIxIiwianRpIjoiOGM3MmY3ZWQ1MzUyNGI3YmJmNDIyYjM0NTUzNzkzYjNmYjY4YjgwMmVlMzY0ZjA1NzkzZmQxODZjOWE2MmZjYWQyZjlkMDUyNGIzNjJiMGQiLCJpYXQiOjE1NTU3NzczNzQsIm5iZiI6MTU1NTc3NzM3NCwiZXhwIjoxNTg3Mzk5Nzc0LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.kCmenGNqYo-ok67eyLgMpad5fRzQstRQv0EPSY7pHD9dsOkr_oTuOPwScbEG0j-5nGK0pBbjv9cmQ2huTd_N75p4WKy4cXzzkbC93mfD5FMhBZg_kkAJ-3Bx0OnZpNBUd-gS1jYMS2fcsN03MfD0oJTychMXMiBqsaiub7rhAMOtEdROQtjBQ7_ZoJTTI2dUgdXybglQwBGuhclMAjK4_Su9YKlVttaqfAeHT2jMwKj2-cn5XPYpSVU2X9EubHbu8bs6mGV7_VHFR_FW5I8odT-GSJth4pA9CyH8HWL56K0x_TftiNSVEbXRdxEmkuZes5d44dRvxwgT8uP4T9UIns2BltZl8j3evh2joDI-aDftgxb_ZN1PkWQz4GH8DegoSjxMbGgWSkGl_2DkIOmuOvM3X45pik7XVeVUGj_Zl4QDqRX3dOsslAUaSeQoQ0gHxH0MqtwV2qlUckYBSFlG6l6LYjWPKwBYTg3VAbjjmMqU313aK8Ru7r4XsK5tBAE5i_FL5jV63treyuPGmUdmYSUaEA5k0rYK8iDqiEPrkYsf0QKOEo7s_QibT18S03Nxnd4ctuWXsr4w9fTBDKPT8mcLuCzA69vH_XyeX-JN8Tj7ioFBLD_BNQgl2DFYHDRESWipsJkJF9NFMMui7WIZA9PCZGrhIy5bo-uZZc1hfC4");

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, StudentActivity.class));
            finish();
        }, 2500);
    }
}

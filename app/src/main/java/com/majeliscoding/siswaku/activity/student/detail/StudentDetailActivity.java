package com.majeliscoding.siswaku.activity.student.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.majeliscoding.siswaku.R;
import com.majeliscoding.siswaku.activity.student.add.StudentAddActivity;
import com.majeliscoding.siswaku.helper.Utils;
import com.majeliscoding.siswaku.model.Student;
import com.majeliscoding.siswaku.service.ApiURL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentDetailActivity extends AppCompatActivity implements StudentDetailContract.View {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnDelete)
    ImageView btnDelete;
    @BindView(R.id.btnEdit)
    ImageView btnEdit;
    @BindView(R.id.relToolbar)
    RelativeLayout relToolbar;
    @BindView(R.id.rivSlipPhoto)
    ImageView rivSlipPhoto;
    @BindView(R.id.tvFullName)
    TextView tvFullName;
    @BindView(R.id.tvGender)
    TextView tvGender;
    @BindView(R.id.tvBornAt)
    TextView tvBornAt;
    @BindView(R.id.tvBirthDate)
    TextView tvBirthDate;
    @BindView(R.id.tvPhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvHobby)
    TextView tvHobby;
    @BindView(R.id.tvProfesion)
    TextView tvProfesion;

    private StudentDetailPresenter mActionListener;
    private Dialog progressDialog;
    private Student datas;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        ButterKnife.bind(this);

        progressDialog  = Utils.progressDialog(this);
        mActionListener = new StudentDetailPresenter(this, this);

        datas = (Student) getIntent().getSerializableExtra("datas");
        loadData();


    }

    @OnClick({R.id.btnBack, R.id.btnDelete, R.id.btnEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnDelete:
                confirmationDialog();
                break;
            case R.id.btnEdit:
                Intent i = new Intent(this, StudentAddActivity.class);
                i.putExtra("datas", datas);
                startActivityForResult(i, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                datas = (Student) data.getSerializableExtra("datas");
                loadData();
            }
        }
    }

    private void loadData() {
        Glide.with(this)
                .setDefaultRequestOptions(new RequestOptions()
                        .placeholder(new ColorDrawable(getResources().getColor(R.color.lightGray)))
//                        .error(context.getDrawable(R.drawable.png_no_image_available_pp))
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .load("https://avatars3.githubusercontent.com/u/26448769?s=400&u=b15a92b4f37739ffd1068dbf671a9126a14b110a&v=4")
//                .load(ApiURL.URL_IMAGE + datas.getPhoto())
                .into(rivSlipPhoto);

        tvFullName.setText(datas.getFirstName() + " " + datas.getLastName());
        tvGender.setText(datas.getGender().equals("M") ? getString(R.string.male) : getString(R.string.female));
        tvBornAt.setText(datas.getBornPlace());
        tvBirthDate.setText(Utils.formatDate("yyyy-MM-dd", "dd MMM yyyy", datas.getBornDate()));
        tvPhoneNumber.setText(datas.getPhone());
        tvAddress.setText(datas.getAddress());
        tvHobby.setText(datas.getHobby().getName());
        tvProfesion.setText(datas.getProfession().getName());
    }

    private void confirmationDialog() {
        final Dialog modals = new Dialog(this);
        modals.requestWindowFeature(Window.FEATURE_NO_TITLE);
        modals.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modals.setContentView(R.layout.modals_confirmation);
        modals.setCanceledOnTouchOutside(false);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        modals.getWindow().setLayout((7 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tvTitle = modals.findViewById(R.id.tvTitle);
        TextView tvDesc = modals.findViewById(R.id.tvDesc);
        Button btnYes = modals.findViewById(R.id.btnYes);
        Button btnNo = modals.findViewById(R.id.btnNo);

        btnNo.setOnClickListener(v -> modals.dismiss());

        btnYes.setOnClickListener(v -> {
            modals.dismiss();
            mActionListener.loadDeleteStudent(datas.getId());
        });

        modals.show();
    }

    @Override
    public void showResultDelete(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showProgressDialog(boolean show) {
        if (show) progressDialog.show();
        else progressDialog.dismiss();
    }

    @Override
    public void showResultError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}


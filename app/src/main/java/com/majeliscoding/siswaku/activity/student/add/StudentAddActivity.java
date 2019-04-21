package com.majeliscoding.siswaku.activity.student.add;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.majeliscoding.siswaku.R;
import com.majeliscoding.siswaku.helper.ImagePicker;
import com.majeliscoding.siswaku.helper.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.majeliscoding.siswaku.model.Student;
import com.majeliscoding.siswaku.service.ApiClient;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;

public class StudentAddActivity extends AppCompatActivity implements StudentAddContract.View, DatePickerDialog.OnDateSetListener  {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.relToolbar)
    RelativeLayout relToolbar;
    @BindView(R.id.civPhoto)
    CircleImageView civPhoto;
    @BindView(R.id.etFirstName)
    TextInputEditText etFirstName;
    @BindView(R.id.etLastName)
    TextInputEditText etLastName;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFemale)
    RadioButton rbFemale;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.etPhoneNumber)
    TextInputEditText etPhoneNumber;
    @BindView(R.id.etPlaceOfBirth)
    TextInputEditText etPlaceOfBirth;
    @BindView(R.id.etDateOfBirth)
    TextInputEditText etDateOfBirth;
    @BindView(R.id.etAddress)
    TextInputEditText etAddress;
    @BindView(R.id.btnSave)
    Button btnSave;

    private static final int PICK_IMAGE_ID = 234;
    private Dialog progressDialog;
    private Bitmap bitmap;
    private String dateOfBirth = "";
    private StudentAddPresenter mActionListener;
    private Student datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);
        ButterKnife.bind(this);

        progressDialog  = Utils.progressDialog(this);
        mActionListener = new StudentAddPresenter(this, this);
        datas           = (Student) getIntent().getSerializableExtra("datas");

        if (datas != null) {
            tvTitle.setText(getResources().getString(R.string.update_data));
            btnSave.setText(getResources().getString(R.string.update));
            dateOfBirth = datas.getBornDate();

            etFirstName.setText(datas.getFirstName());
            etLastName.setText(datas.getLastName());
            etPlaceOfBirth.setText(datas.getBornPlace());
            etDateOfBirth.setText(Utils.formatDate("yyyy-MM-dd", "dd MMM yyyy", dateOfBirth));
            etAddress.setText(datas.getAddress());
            etPhoneNumber.setText(datas.getPhone());

            if (datas.getGender().equals("M")) rbMale.setChecked(true);
            else rbFemale.setChecked(true);
        }
    }

    @OnClick({R.id.btnBack, R.id.civPhoto, R.id.etDateOfBirth, R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.civPhoto:
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
                break;
            case R.id.etDateOfBirth:
                if (dateOfBirth.equals("")) {
                    showDateDialog(1990, 0, 1, R.style.DatePickerSpinner);
                } else {
                    String[] date = dateOfBirth.split("-");
                    showDateDialog(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]), R.style.DatePickerSpinner);
                }
                break;
            case R.id.btnSave:
                HashMap<String, RequestBody> params = new HashMap<>();
                params.put("first_name", ApiClient.createRequestBody(etFirstName.getText().toString()));
                params.put("last_name", ApiClient.createRequestBody(etLastName.getText().toString()));
                params.put("gender", ApiClient.createRequestBody(rbMale.isChecked()?"M":"F"));
                params.put("born_place", ApiClient.createRequestBody(etPlaceOfBirth.getText().toString()));
                params.put("born_date", ApiClient.createRequestBody(dateOfBirth));
                params.put("phone", ApiClient.createRequestBody(etPhoneNumber.getText().toString()));
                params.put("address", ApiClient.createRequestBody(etAddress.getText().toString()));
                params.put("profession_id", ApiClient.createRequestBody("1"));
                params.put("hobby_id", ApiClient.createRequestBody("1"));

                if (datas == null)
                     mActionListener.loadCreateStudent(params, createFile(bitmap));
                else
                    mActionListener.loadUpdateStudent(datas.getId(),params, createFile(toBitmap(civPhoto)));

                break;
        }
    }

    @Override
    public void showResultCreate(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showResultUpdate(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        datas.setFirstName(etFirstName.getText().toString());
        datas.setLastName(etLastName.getText().toString());
        datas.setGender(rbMale.isChecked()?"M":"F");
        datas.setBornPlace(etPlaceOfBirth.getText().toString());
        datas.setBornDate(dateOfBirth);
        datas.setPhone(etPhoneNumber.getText().toString());
        datas.setAddress(etAddress.getText().toString());
        datas.getHobby().setName(datas.getHobby().getName());
        datas.getProfession().setName(datas.getProfession().getName());

        Intent i = new Intent();
        i.putExtra("datas", datas);
        setResult(RESULT_OK, i);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE_ID) {
            if (resultCode == RESULT_OK) {
                bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                civPhoto.setImageBitmap(bitmap);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void showDateDialog(int year, int monthOfYear, int dayOfMonth, int spinnerTheme) {
        new SpinnerDatePickerDialogBuilder()
                .context(this)
                .callback(this)
                .spinnerTheme(spinnerTheme)
                .defaultDate(year, monthOfYear, dayOfMonth)
                .build()
                .show();
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        etDateOfBirth.setText(sdf1.format(calendar.getTime()));
        etDateOfBirth.setError(null);
        dateOfBirth = sdf2.format(calendar.getTime());
    }

    private File createFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                , System.currentTimeMillis() + "_image.jpeg");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private Bitmap toBitmap(CircleImageView view){
        Bitmap bitmap = ((BitmapDrawable)view.getDrawable()).getBitmap();
        return bitmap;
    }
}

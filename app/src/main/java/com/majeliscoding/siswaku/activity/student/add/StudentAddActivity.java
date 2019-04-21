package com.majeliscoding.siswaku.activity.student.add;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.majeliscoding.siswaku.R;
import com.majeliscoding.siswaku.adapter.HobbyAdapter;
import com.majeliscoding.siswaku.adapter.SpinnerAdapter;
import com.majeliscoding.siswaku.helper.ImagePicker;
import com.majeliscoding.siswaku.helper.Utils;
import com.majeliscoding.siswaku.model.Hobby;
import com.majeliscoding.siswaku.model.Profession;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;

public class StudentAddActivity extends AppCompatActivity implements StudentAddContract.View, DatePickerDialog.OnDateSetListener {

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
    @BindView(R.id.spProfesion)
    AppCompatSpinner spProfesion;
    @BindView(R.id.rvHobby)
    RecyclerView rvHobby;

    private static final int PICK_IMAGE_ID = 234;
    @BindView(R.id.relPhoto)
    RelativeLayout relPhoto;
    private Dialog progressDialog;
    private Bitmap bitmap;
    private String dateOfBirth = "";
    private StudentAddPresenter mActionListener;
    private Student datas;
    private HobbyAdapter hobbyAdapter;
    private String idHobby, idProfession, hobby, profession;
    private List<Hobby> listHobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);
        ButterKnife.bind(this);

        progressDialog = Utils.progressDialog(this);
        mActionListener = new StudentAddPresenter(this, this);
        datas = (Student) getIntent().getSerializableExtra("datas");

        mActionListener.loadListProfession();
        mActionListener.loadListHobby();

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

            Glide.with(this)
                    .setDefaultRequestOptions(new RequestOptions()
                            .placeholder(new ColorDrawable(getResources().getColor(R.color.lightGray)))
//                        .error(context.getDrawable(R.drawable.png_no_image_available_pp))
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                    .load("https://avatars3.githubusercontent.com/u/26448769?s=400&u=b15a92b4f37739ffd1068dbf671a9126a14b110a&v=4")
//                    .load(ApiURL.URL_IMAGE + datas.getPhoto())
                    .into(civPhoto);
        }
    }

    @OnClick({R.id.btnBack, R.id.relPhoto, R.id.civPhoto, R.id.etDateOfBirth, R.id.btnSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.civPhoto:
                Intent chooseImageIntent1 = ImagePicker.getPickImageIntent(this);
                startActivityForResult(chooseImageIntent1, PICK_IMAGE_ID);
                break;
            case R.id.relPhoto:
                Intent chooseImageIntent2 = ImagePicker.getPickImageIntent(this);
                startActivityForResult(chooseImageIntent2, PICK_IMAGE_ID);
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
                for (int i = 0; i < listHobby.size(); i++) {
                    if (listHobby.get(i).getSelected()) {
                        idHobby = listHobby.get(i).getId().toString();
                        hobby   = listHobby.get(i).getName();
                    }
                }

                HashMap<String, RequestBody> params = new HashMap<>();
                params.put("first_name", ApiClient.createRequestBody(etFirstName.getText().toString()));
                params.put("last_name", ApiClient.createRequestBody(etLastName.getText().toString()));
                params.put("gender", ApiClient.createRequestBody(rbMale.isChecked() ? "M" : "F"));
                params.put("born_place", ApiClient.createRequestBody(etPlaceOfBirth.getText().toString()));
                params.put("born_date", ApiClient.createRequestBody(dateOfBirth));
                params.put("phone", ApiClient.createRequestBody(etPhoneNumber.getText().toString()));
                params.put("address", ApiClient.createRequestBody(etAddress.getText().toString()));
                params.put("profession_id", ApiClient.createRequestBody(idProfession));
                params.put("hobby_id", ApiClient.createRequestBody(idHobby));

                if (datas == null)
                    mActionListener.loadCreateStudent(params, createFile(bitmap));
                else
                    mActionListener.loadUpdateStudent(datas.getId(), params, createFile(toBitmap(civPhoto)));

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
        datas.setGender(rbMale.isChecked() ? "M" : "F");
        datas.setBornPlace(etPlaceOfBirth.getText().toString());
        datas.setBornDate(dateOfBirth);
        datas.setPhone(etPhoneNumber.getText().toString());
        datas.setAddress(etAddress.getText().toString());
        datas.getHobby().setId(Integer.parseInt(idHobby));
        datas.getHobby().setName(hobby);
        datas.getProfession().setId(Integer.parseInt(idProfession));
        datas.getProfession().setName(profession);

        Intent i = new Intent();
        i.putExtra("datas", datas);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void showListHobby(List<Hobby> data) {
        listHobby = new ArrayList<>();
        listHobby.addAll(data);

        if (datas != null) {
            for (int i = 0; i < listHobby.size(); i++) {
                if (listHobby.get(i).getName().equals(datas.getHobby().getName())) {
                    listHobby.get(i).setSelected(true);
                    idHobby = listHobby.get(i).getId().toString();
                    hobby   = listHobby.get(i).getName();
                }
            }
        }

        hobbyAdapter = new HobbyAdapter(this, listHobby);
        rvHobby.setLayoutManager(new GridLayoutManager(this, 2));
        rvHobby.setAdapter(hobbyAdapter);


    }

    @Override
    public void showListProfession(List<Profession> data) {
        ArrayList<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.select_profession));

        for (Profession item : data) {
            list.add(item.getName());
        }

        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_item, list);
        spProfesion.setAdapter(adapter);
        spProfesion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    idProfession = data.get(position - 1).getId().toString();
                    profession = data.get(position - 1).getName();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (datas != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(datas.getProfession().getName())) {
                    spProfesion.setSelection(i);
                    idProfession = datas.getProfession().getId().toString();
                    profession   = datas.getProfession().getName();
                }
            }
        }

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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
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

    private Bitmap toBitmap(CircleImageView view) {
        Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
        return bitmap;
    }
}

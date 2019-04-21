package com.majeliscoding.siswaku.activity.student.detail;

import android.content.Context;

import com.majeliscoding.siswaku.helper.DataConfig;
import com.majeliscoding.siswaku.model.ResponseApi;
import com.majeliscoding.siswaku.service.ApiClient;
import com.majeliscoding.siswaku.service.modul.StudentService;

import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentDetailPresenter implements StudentDetailContract.UserActionListener {

    private StudentDetailContract.View mView;
    private StudentService studentService;
    private Context context;


    public StudentDetailPresenter(Context context, StudentDetailContract.View mView) {
        this.context        = context;
        this.mView          = mView;
        studentService      = ApiClient.getClient().create(StudentService.class);
    }



    @Override
    public void loadDeleteStudent(Integer id) {
        mView.showProgressDialog(true);
        Call<ResponseApi> call = studentService.deleteStudent(
                DataConfig.getString(context, DataConfig.TOKEN), id);
        call.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                mView.showProgressDialog(false);
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        mView.showResultDelete(response.body().getMessage());
                    } else {
                        mView.showResultError(response.body().getMessage());
                    }
                } else {
                    mView.showResultError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                mView.showProgressDialog(false);
                mView.showResultError(t.getMessage());
                call.cancel();
            }
        });
    }
}

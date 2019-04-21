package com.majeliscoding.siswaku.activity.student;

import android.content.Context;

import com.majeliscoding.siswaku.helper.DataConfig;
import com.majeliscoding.siswaku.model.ResponseApiPage;
import com.majeliscoding.siswaku.model.Student;
import com.majeliscoding.siswaku.service.ApiClient;
import com.majeliscoding.siswaku.service.modul.StudentService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentPresenter implements StudentContract.UserActionListener {

    private StudentContract.View mView;
    private StudentService studentService;
    private Context context;


    public StudentPresenter(Context context, StudentContract.View mView) {
        this.context   = context;
        this.mView     = mView;
        studentService = ApiClient.getClient().create(StudentService.class);
    }

    @Override
    public void loadListStudent(final int page) {
        mView.showProgressDialog(page == 1);
        mView.showLoadingPage(page != 1);
        Call<ResponseApiPage<Student>> call = studentService.getListStudent(DataConfig.getString(context, DataConfig.TOKEN), page);
        call.enqueue(new Callback<ResponseApiPage<Student>>() {
            @Override
            public void onResponse(Call<ResponseApiPage<Student>> call, Response<ResponseApiPage<Student>> response) {
                mView.showProgressDialog(false);
                mView.showLoadingPage(false);
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        mView.showListStudent(response.body());
                    } else {
                        mView.showResultError(response.body().getMessage());
                    }
                } else {
                    mView.showResultError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseApiPage<Student>> call, Throwable t) {
                mView.showProgressDialog(false);
                mView.showLoadingPage(false);
                mView.showResultError(t.getMessage());
                call.cancel();
            }
        });
    }


}

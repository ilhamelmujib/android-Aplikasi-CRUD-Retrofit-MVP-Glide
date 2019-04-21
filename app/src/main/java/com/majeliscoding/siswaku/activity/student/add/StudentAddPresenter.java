package com.majeliscoding.siswaku.activity.student.add;

import android.content.Context;

import com.majeliscoding.siswaku.helper.DataConfig;
import com.majeliscoding.siswaku.model.Hobby;
import com.majeliscoding.siswaku.model.Profession;
import com.majeliscoding.siswaku.model.ResponseApi;
import com.majeliscoding.siswaku.service.ApiClient;
import com.majeliscoding.siswaku.service.modul.StudentService;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentAddPresenter implements StudentAddContract.UserActionListener {

    private StudentAddContract.View mView;
    private StudentService studentService;
    private Context context;


    public StudentAddPresenter(Context context, StudentAddContract.View mView) {
        this.context        = context;
        this.mView          = mView;
        studentService      = ApiClient.getClient().create(StudentService.class);
    }


    @Override
    public void loadCreateStudent(HashMap<String, RequestBody> params, File file) {
        mView.showProgressDialog(true);
        Call<ResponseApi> call = studentService.postCreateStudent(
                DataConfig.getString(context, DataConfig.TOKEN), params, ApiClient.createFormData(file, "foto"));
        call.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                mView.showProgressDialog(false);
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        mView.showResultCreate(response.body().getMessage());
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

    @Override
    public void loadUpdateStudent(Integer id, HashMap<String, RequestBody> params, File file) {
        mView.showProgressDialog(true);
        Call<ResponseApi> call = studentService.putUpdateStudent(
                DataConfig.getString(context, DataConfig.TOKEN), id,params, ApiClient.createFormData(file, "foto"));
        call.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                mView.showProgressDialog(false);
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        mView.showResultUpdate(response.body().getMessage());
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

    @Override
    public void loadListHobby() {
        mView.showProgressDialog(true);
        Call<ResponseApi<List<Hobby>>> call = studentService.getListHobby(DataConfig.getString(context, DataConfig.TOKEN));
        call.enqueue(new Callback<ResponseApi<List<Hobby>>>() {
            @Override
            public void onResponse(Call<ResponseApi<List<Hobby>>> call, Response<ResponseApi<List<Hobby>>> response) {
                mView.showProgressDialog(false);
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        mView.showListHobby(response.body().getData());
                    } else {
                        mView.showResultError(response.body().getMessage());
                    }
                } else {
                    mView.showResultError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<List<Hobby>>> call, Throwable t) {
                mView.showProgressDialog(false);
                mView.showResultError(t.getMessage());
                call.cancel();
            }
        });
    }

    @Override
    public void loadListProfession() {
        mView.showProgressDialog(true);
        Call<ResponseApi<List<Profession>>> call = studentService.getListProfession(DataConfig.getString(context, DataConfig.TOKEN));
        call.enqueue(new Callback<ResponseApi<List<Profession>>>() {
            @Override
            public void onResponse(Call<ResponseApi<List<Profession>>> call, Response<ResponseApi<List<Profession>>> response) {
                mView.showProgressDialog(false);
                if (response.body() != null) {
                    if (response.body().getCode() == 200) {
                        mView.showListProfession(response.body().getData());
                    } else {
                        mView.showResultError(response.body().getMessage());
                    }
                } else {
                    mView.showResultError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<List<Profession>>> call, Throwable t) {
                mView.showProgressDialog(false);
                mView.showResultError(t.getMessage());
                call.cancel();
            }
        });
    }
}

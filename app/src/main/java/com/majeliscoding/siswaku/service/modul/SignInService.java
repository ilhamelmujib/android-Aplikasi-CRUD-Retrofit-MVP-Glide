package com.majeliscoding.siswaku.service.modul;

import com.majeliscoding.siswaku.model.ResponseApi;
import com.majeliscoding.siswaku.model.Student;
import com.majeliscoding.siswaku.service.ApiURL;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface SignInService {


    @Headers("Content-Type: application/json")
    @POST(ApiURL.LOGIN)
    Call<ResponseApi> postLogin(
            @Body RequestBody params
    );

}

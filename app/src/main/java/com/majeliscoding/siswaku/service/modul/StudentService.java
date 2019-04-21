package com.majeliscoding.siswaku.service.modul;

import com.majeliscoding.siswaku.model.Hobby;
import com.majeliscoding.siswaku.model.Profession;
import com.majeliscoding.siswaku.model.ResponseApi;
import com.majeliscoding.siswaku.model.ResponseApiPage;
import com.majeliscoding.siswaku.model.Student;
import com.majeliscoding.siswaku.service.ApiURL;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudentService {

    @GET(ApiURL.GET_STUDENT)
    Call<ResponseApiPage<Student>> getListStudent(
        @Header("Authorization") String token,
        @Query("page") int page
    );

    @Multipart
    @POST(ApiURL.CREATE_STUDENT)
    Call<ResponseApi> postCreateStudent(
        @Header("Authorization") String token,
        @PartMap HashMap<String, RequestBody> params,
        @Part MultipartBody.Part picture
    );

    @Multipart
    @POST(ApiURL.UPDATE_STUDENT)
    Call<ResponseApi> putUpdateStudent(
        @Header("Authorization") String token,
        @Path("id") Integer id,
        @PartMap HashMap<String, RequestBody> params,
        @Part MultipartBody.Part picture
    );

    @DELETE(ApiURL.DELETE_STUDENT)
    Call<ResponseApi> deleteStudent(
        @Header("Authorization") String token,
        @Path("id") Integer id
    );

    @GET(ApiURL.LIST_HOBBY)
    Call<ResponseApi<List<Hobby>>> getListHobby(
            @Header("Authorization") String token
    );

    @GET(ApiURL.LIST_PROFESSION)
    Call<ResponseApi<List<Profession>>> getListProfession(
            @Header("Authorization") String token
    );


}

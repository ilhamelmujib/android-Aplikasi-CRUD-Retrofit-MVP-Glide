package com.majeliscoding.siswaku.activity.student.add;

import com.majeliscoding.siswaku.model.Hobby;
import com.majeliscoding.siswaku.model.Profession;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;

public class StudentAddContract {

    interface View {
        void showResultCreate(String msg);
        void showResultUpdate(String msg);
        void showListHobby(List<Hobby> data);
        void showListProfession(List<Profession> data);
        void showProgressDialog(boolean show);
        void showResultError(String msg);
    }

    interface UserActionListener {
        void loadCreateStudent(HashMap<String, RequestBody> params, File file);
        void loadUpdateStudent(Integer id, HashMap<String, RequestBody> params, File file);
        void loadListHobby();
        void loadListProfession();
    }

}

package com.majeliscoding.siswaku.activity.student.add;

import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;

public class StudentAddContract {

    interface View {
        void showResultCreate(String msg);
        void showResultUpdate(String msg);
        void showProgressDialog(boolean show);
        void showResultError(String msg);
    }

    interface UserActionListener {
        void loadCreateStudent(HashMap<String, RequestBody> params, File file);
        void loadUpdateStudent(Integer id, HashMap<String, RequestBody> params, File file);
    }

}

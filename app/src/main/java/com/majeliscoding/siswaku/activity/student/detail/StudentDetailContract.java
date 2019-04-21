package com.majeliscoding.siswaku.activity.student.detail;

import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;

public class StudentDetailContract {

    interface View {
        void showResultDelete(String msg);
        void showProgressDialog(boolean show);
        void showResultError(String msg);
    }

    interface UserActionListener {
        void loadDeleteStudent(Integer id);
    }

}

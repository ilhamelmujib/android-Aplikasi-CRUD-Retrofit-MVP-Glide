package com.majeliscoding.siswaku.activity.student;

import com.majeliscoding.siswaku.model.ResponseApiPage;
import com.majeliscoding.siswaku.model.Student;

import java.util.List;

public class StudentContract {

    interface View {
        void showListStudent(ResponseApiPage<Student> data);
        void showProgressDialog(boolean show);
        void showLoadingPage(boolean show);
        void showResultError(String msg);
    }

    interface UserActionListener {
        void loadListStudent(final int page);
    }

}

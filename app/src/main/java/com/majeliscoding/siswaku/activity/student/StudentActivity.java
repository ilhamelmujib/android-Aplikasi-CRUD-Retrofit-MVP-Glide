package com.majeliscoding.siswaku.activity.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.majeliscoding.siswaku.R;
import com.majeliscoding.siswaku.activity.student.add.StudentAddActivity;
import com.majeliscoding.siswaku.adapter.StudentAdapter;
import com.majeliscoding.siswaku.model.ResponseApiPage;
import com.majeliscoding.siswaku.model.Student;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentActivity extends AppCompatActivity implements StudentContract.View {

    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnAdd)
    ImageView btnAdd;
    @BindView(R.id.relToolbar)
    RelativeLayout relToolbar;
    @BindView(R.id.rvStudent)
    RecyclerView rvStudent;
    @BindView(R.id.customLoading)
    AVLoadingIndicatorView customLoading;
    @BindView(R.id.linCustomLoading)
    LinearLayout linCustomLoading;
    @BindView(R.id.tvTitleError)
    TextView tvTitleError;
    @BindView(R.id.tvDescError)
    TextView tvDescError;
    @BindView(R.id.tvDescErrorDetail)
    TextView tvDescErrorDetail;
    @BindView(R.id.btnTryAgain)
    Button btnTryAgain;
    @BindView(R.id.relError)
    RelativeLayout relError;
    @BindView(R.id.relEmpty)
    RelativeLayout relEmpty;

    private StudentPresenter mActionListener;
    private StudentAdapter studentAdapter;
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        ButterKnife.bind(this);

        mActionListener     = new StudentPresenter(this, this);
        studentAdapter      = new StudentAdapter(this, new ArrayList<>());
        gridLayoutManager   = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this);
        rvStudent.setLayoutManager(gridLayoutManager);
        rvStudent.setAdapter(studentAdapter);
        rvStudent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        if (!isLastPage) {
                            currentPage++;
                            mActionListener.loadListStudent(currentPage);
                        }
                    }
                }
            }
        });

    }

    @Override
    public void showListStudent(ResponseApiPage<Student> data) {
        if (data.getData().size() == 0) {
            relEmpty.setVisibility(View.VISIBLE);
            rvStudent.setVisibility(View.GONE);
        } else {
            relEmpty.setVisibility(View.GONE);
            rvStudent.setVisibility(View.VISIBLE);
            studentAdapter.addAll(data.getData());
            if (studentAdapter.getItemCount() >= data.getTotal()){
                isLastPage = true;
            }
        }
    }

    @Override
    public void showProgressDialog(boolean show) {
        if (show) {
            relError.setVisibility(View.GONE);
            relEmpty.setVisibility(View.GONE);
            rvStudent.setVisibility(View.GONE);
            linCustomLoading.setVisibility(View.VISIBLE);
        } else {
            rvStudent.setVisibility(View.VISIBLE);
            linCustomLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingPage(boolean show) {
        if (show) {
            isLoading = true;
            studentAdapter.addLoadingFooter();
        } else {
            isLoading = false;
            studentAdapter.removeLoadingFooter();
        }
    }

    @Override
    public void showResultError(String msg) {
        tvDescErrorDetail.setText(msg);
        relError.setVisibility(View.VISIBLE);
        rvStudent.setVisibility(View.GONE);
    }

    @OnClick({R.id.btnAdd, R.id.btnTryAgain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                startActivity(new Intent(this, StudentAddActivity.class));
                break;
            case R.id.btnTryAgain:
                reloadPage();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadPage();
    }

    private void reloadPage(){
        isLoading = false;
        isLastPage = false;
        currentPage = 1;
        studentAdapter.clear();
        mActionListener.loadListStudent(currentPage);
    }
}

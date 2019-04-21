package com.majeliscoding.siswaku.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.majeliscoding.siswaku.R;
import com.majeliscoding.siswaku.activity.student.detail.StudentDetailActivity;
import com.majeliscoding.siswaku.model.Student;
import com.majeliscoding.siswaku.service.ApiURL;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Student> list;
    private Context context;
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    public StudentAdapter(Context context, List<Student> list) {
        this.context = context;
        this.list = list;
    }

    public List<Student> getList() {
        return this.list;
    }

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(View view, List<Student> obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
        notifyDataSetChanged();
    }


    private void add(Student r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<Student> list) {
        for (Student r : list) {
            add(r);
        }
    }

    public void remove(Student r) {
        int position = list.indexOf(r);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Student());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        if (list.size() != 0) {
            int position = list.size() - 1;
            Student r = getItem(position);
            if (r != null) {
                list.remove(position);
                notifyItemRemoved(position);
            }
        }
    }

    private Student getItem(int position) {
        return list.get(position);
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v = inflater.inflate(R.layout.list_student, parent, false);
        viewHolder = new StudentAdapter.ViewHolder(v);
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewLoading(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v = inflater.inflate(R.layout.list_loading, parent, false);
        viewHolder = new StudentAdapter.LoadingPage(v);
        return viewHolder;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                viewHolder = getViewLoading(parent, inflater);
                break;
        }

        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final @NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case ITEM:
                final ViewHolder viewHolder = (ViewHolder) holder;
                Glide.with(context)
                        .setDefaultRequestOptions(new RequestOptions()
                                .placeholder(new ColorDrawable(context.getResources().getColor(R.color.lightGray)))
//                        .error(context.getDrawable(R.drawable.png_no_image_available_pp))
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .load("https://avatars3.githubusercontent.com/u/26448769?s=400&u=b15a92b4f37739ffd1068dbf671a9126a14b110a&v=4")
//                        .load(ApiURL.URL_IMAGE + list.get(position).getPhoto())
                        .into(viewHolder.rivPhoto);

                viewHolder.tvLocation.setText(list.get(position).getAddress());
                viewHolder.tvName.setText(list.get(position).getFirstName() + " " + list.get(position).getLastName());
                viewHolder.tvGender.setText(list.get(position).getGender().equals("M")?context.getString(R.string.male):context.getString(R.string.female));
                viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, StudentDetailActivity.class);
                        i.putExtra("datas", list.get(position));
                        context.startActivity(i);
                    }
                });
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rivPhoto)
        RoundedImageView rivPhoto;
        @BindView(R.id.tvLocation)
        TextView tvLocation;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvGender)
        TextView tvGender;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    protected class LoadingPage extends RecyclerView.ViewHolder {
        LoadingPage(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

}
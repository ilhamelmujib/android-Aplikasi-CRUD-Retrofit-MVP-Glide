package com.majeliscoding.siswaku.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.majeliscoding.siswaku.R;
import com.majeliscoding.siswaku.model.Hobby;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HobbyAdapter extends RecyclerView.Adapter<HobbyAdapter.ViewHolder> {

    private boolean onBind;
    private List<Hobby> list;
    private Context context;

    public HobbyAdapter(Context context, List<Hobby> list) {
        this.context = context;
        this.list = list;
    }

    public List<Hobby> getList() {
        return this.list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_hobby, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final @NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.cbHobby.setText(list.get(position).getName());
        holder.cbHobby.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!onBind) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setSelected(false);
                    notifyItemChanged(i);
                }
                list.get(position).setSelected(isChecked);
                notifyItemChanged(position);
            }
        });
        onBind = true;
        holder.cbHobby.setChecked(list.get(position).getSelected());
        onBind = false;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cbHobby)
        CheckBox cbHobby;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
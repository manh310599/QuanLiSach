package com.example.quanlisach.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlisach.R;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    private final List<User> users;
    private final Context context;


    public UserAdapter(List<User> users, Context context) {
        this.context = context;
        this.users = users;

    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_pro, parent, false);
        return new UserViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = users.get(position);


        if (user == null) {
            return;
        }


            Glide.with(context).load(users.get(position).getAvatar()).into(holder.imgUser);
            holder.NameUser.setText(user.getAccount());


}

    @Override
    public int getItemCount() {
        if (users != null)
        {
            return users.size();
        }
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgUser;
        private final TextView NameUser;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUser = itemView.findViewById(R.id.imgUser);
            NameUser = itemView.findViewById(R.id.NameUser);

        }
    }
}
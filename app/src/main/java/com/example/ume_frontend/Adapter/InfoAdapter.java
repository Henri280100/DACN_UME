package com.example.ume_frontend.Adapter;

import android.content.Context;
import android.icu.text.IDNA;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ume_frontend.Model.UserModel;
import com.example.ume_frontend.R;
import com.example.ume_frontend.ui.CircleTransform;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.myViewHolder> {
    Context mContext;
    UserModel.Data account;

    public InfoAdapter(UserModel.Data account) {
        this.account = account;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        UserModel.Data myAccount = account;
        Glide.with(mContext).load(myAccount.getUrlAvarta())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.userAvatarImg);
        holder.txtUserName.setText(myAccount.getUserName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        CircularImageView userAvatarImg;
        Button btnAddFriend, btnUnFriend;
        Button btnChat;
        TextView txtUserName;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatarImg = itemView.findViewById(R.id.userAvatarImg);
            btnAddFriend = (Button) itemView.findViewById(R.id.btnAddFriend);
            btnUnFriend = (Button) itemView.findViewById(R.id.btnUnFriend);
            btnChat = (Button) itemView.findViewById(R.id.btnChat);
            txtUserName = itemView.findViewById(R.id.txtUserName);
        }
    }


}

package com.example.ume_frontend.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ume_frontend.R;
import com.example.ume_frontend.Model.PosterModel;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.myViewHolder> {

    Context mContext;
    List<PosterModel.Data> posterList;
    List<PosterModel.Data> posterOldList;


    public PosterAdapter(List<PosterModel.Data> posterList) {
        this.posterList = posterList;
        this.posterOldList = posterList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        PosterModel.Data posterData = posterList.get(position);


        if (posterData == null) {
            return;
        } else {
            Picasso.get().load(posterData.getImgPoster()).into(holder.imgPoster);
            holder.txtStatus.setText(posterData.getContent());

        }



    }

    @Override
    public int getItemCount() {
        if (posterList != null) {
            return posterList.size();
        }

        return 0;
    }


    public static class myViewHolder extends RecyclerView.ViewHolder {
        CircularImageView imgAvatar;
        TextView txtStatus;
        TextView txtUserName;
        ImageView imgPoster;
        EditText txtInputcomment;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imAvatar4);
            txtInputcomment = itemView.findViewById(R.id.txtInputcomment);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            imgPoster = itemView.findViewById(R.id.imPoster);
            txtStatus = itemView.findViewById(R.id.txtStatus1);
        }
    }
}

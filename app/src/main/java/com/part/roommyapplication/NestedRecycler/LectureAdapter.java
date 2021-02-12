package com.part.roommyapplication.NestedRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.part.roommyapplication.R;
import com.part.roommyapplication.Room.Table.Content;

import java.util.List;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.MyLectureHolder> {
    Context mContext;
    private List<Content> contents;

    public LectureAdapter(Context mContext, List<Content> contents) {
        this.mContext = mContext;
        this.contents = contents;

    }

    @NonNull
    @Override
    public MyLectureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lecture_item, parent, false);
        return new MyLectureHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLectureHolder holder, int position) {
       Content lecture =contents.get(position);
        holder.tvSubItemTitle.setText(lecture.getTitle());

        holder.linearLayoutVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "clicked in the Linear Layout", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class MyLectureHolder extends RecyclerView.ViewHolder {

        TextView tvSubItemTitle;
        LinearLayout linearLayoutVideo;
        ImageView btnDownload;
        CardView cvMain;

        public MyLectureHolder(@NonNull View itemView) {
            super(itemView);
            tvSubItemTitle = itemView.findViewById(R.id.tv_lecture_title);
            linearLayoutVideo = itemView.findViewById(R.id.linear_layout_video);
            btnDownload = itemView.findViewById(R.id.btn_download_video);
            cvMain = itemView.findViewById(R.id.cv_main);
        }
    }
}

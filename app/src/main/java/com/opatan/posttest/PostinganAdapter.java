package com.opatan.posttest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class PostinganAdapter extends RecyclerView.Adapter<PostinganAdapter.PostinganViewHolder> {

    Context context;
    OnUserClickListener listener;
    List<DataPostingan> dataPostingansList;

    public PostinganAdapter(Context context, OnUserClickListener listener, List<DataPostingan> dataPostinganList) {
        this.context = context;
        this.listener = listener;
        this.dataPostingansList = dataPostinganList;
    }

    public interface OnUserClickListener{
        void onUserClick(String judul, String deskripsi, String waktu, String id);
    }

    @NonNull
    @Override
    public PostinganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.format_recycler_view,parent,false);
        PostinganViewHolder postinganViewHolder = new PostinganViewHolder(view);
        return postinganViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PostinganViewHolder holder, int position) {
        final DataPostingan dataPostingan  = dataPostingansList.get(position);
        holder.judul.setText(dataPostingan.getJudul());
        holder.deksripsi.setText(dataPostingan.getDeksripsi());
        holder.waktu.setText(dataPostingan.getWaktu());

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUserClick(dataPostingan.getJudul(),dataPostingan.getDeksripsi(),dataPostingan.getWaktu(), dataPostingan.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataPostingansList.size();
    }

    public class PostinganViewHolder extends ViewHolder{

        private CardView parent;
        private TextView judul, deksripsi, waktu;

        public PostinganViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent_card_view);
            judul = itemView.findViewById(R.id.judul);
            deksripsi = itemView.findViewById(R.id.deskripsi);
            waktu = itemView.findViewById(R.id.timestamp);

        }
    }
}
package com.example.dell.filmcinema;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Cinema_MovieAdapter extends RecyclerView.Adapter<Cinema_MovieAdapter.MyViewHolder> {
    ArrayList<Film> films=new ArrayList<>();
    Context context;

    public Cinema_MovieAdapter(ArrayList<Film> films,Context context) {
        this.films = films;
        this.context=context;
    }

    @NonNull
    @Override
    public Cinema_MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cinemafilm,parent, false);
        return new Cinema_MovieAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Cinema_MovieAdapter.MyViewHolder holder, int position) {
        final Film film=films.get(position);
        Glide
                .with(context)
                .load(film.getImage())
                .into(holder.imageView);
        holder.txtName.setText(film.getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(isLongClick){
                    Intent intent=new Intent(context,DetailFilm.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("film",film);
                    context.startActivity(intent);

                }else{
                    Intent intent=new Intent(context,DetailFilm.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("film",film);
                    context.startActivity(intent);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return films.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        ImageView imageView;
        TextView txtName;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageItem);
            txtName=itemView.findViewById(R.id.txtName);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);

        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }

}

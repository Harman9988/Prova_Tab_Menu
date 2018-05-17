package com.example.alber.prova_tab_menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListView extends RecyclerView.Adapter<AdaptadorListView.ProductViewHolder>  {

    private List<Alumnes> alumnelist;
    Context context;

    public AdaptadorListView(List<Alumnes> alumnelist, Context context) {
        this.alumnelist = alumnelist;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listview, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Alumnes alumne = alumnelist.get( position );

        Glide.with( context).load( alumne.getImage() ).into( holder.imageView );

        holder.textViewnom.setText( alumne.getNom() );
        holder.textViewcognom.setText( alumne.getCognom() );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return alumnelist.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView textViewnom, textViewcognom;
        ImageView imageView;
        public ProductViewHolder(View itemView) {
            super( itemView );
            textViewnom = itemView.findViewById(R.id.nomalumne);
            textViewcognom = itemView.findViewById(R.id.cognomalumne);
            imageView = itemView.findViewById( R.id.imagealumne );

        }


    }
}

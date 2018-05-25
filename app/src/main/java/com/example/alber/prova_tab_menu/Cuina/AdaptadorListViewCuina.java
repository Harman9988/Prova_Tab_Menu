package com.example.alber.prova_tab_menu.Cuina;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alber.prova_tab_menu.R;

import java.util.List;

public class AdaptadorListViewCuina extends RecyclerView.Adapter<AdaptadorListViewCuina.ProductViewHolder> {

    private List<Observacio_IdAlumne> cuinalist;
    Context context;

    public AdaptadorListViewCuina (List<Observacio_IdAlumne> cuinalist,Context context){
        this.cuinalist = cuinalist;
        this.context=context;
    }

    public void setData(List<Observacio_IdAlumne> newData) {
        this.cuinalist.clear();
        cuinalist.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate( R.layout.listviewobservacio, null);
        return new AdaptadorListViewCuina.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListViewCuina.ProductViewHolder holder, int position) {
        final Observacio_IdAlumne observacio = cuinalist.get( position );
        holder.textviewobservacio.setText(observacio.getObservacio());
        holder.textViewcount.setText((Integer.toString(observacio.getContador_alumnes())));
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cuinalist.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textviewobservacio, textViewcount;

        public ProductViewHolder(View itemView) {
            super( itemView );
            textviewobservacio = itemView.findViewById( R.id.observacio );
            textViewcount = itemView.findViewById( R.id.countalumnes );
        }
    }
}

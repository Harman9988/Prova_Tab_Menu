package com.example.alber.prova_tab_menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListView extends RecyclerView.Adapter<AdaptadorListView.ProductViewHolder>  {

    private List<Alumne> alumnelist;
    Context context;
    boolean checkbox = false;

    public AdaptadorListView(List<Alumne> alumnelist, Context context) {
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
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, int position) {
        final Alumne alumne = alumnelist.get( position );
        Glide.with( context).load( alumne.getImage() ).into( holder.imageView );

        holder.textViewnom.setText( alumne.getNom() );
        holder.textViewcognom.setText( alumne.getCognom() );

        //in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        holder.checkBox.setChecked(alumne.isChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alumne.setChecked(isChecked);
            }
        });
        holder.setItemClickListener( new ItemClassListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                    Toast.makeText( context, "Long Click:"+alumnelist.get( position ), Toast.LENGTH_SHORT ).show();
            }
        } );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return alumnelist.size();
    }

    public List<Alumne> getAlumnesChecked(){
        List<Alumne> llistaRetornar=new ArrayList<>();

        for (Alumne alumne : alumnelist) {
            if(alumne.isChecked()){
                llistaRetornar.add(alumne);
            }
        }
        return llistaRetornar;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener,View.OnLongClickListener{
        TextView textViewnom, textViewcognom;
        ImageView imageView;
        CheckBox checkBox;

        private SparseBooleanArray itemStateArray= new SparseBooleanArray();
        private ItemClassListener itemClassListener;
        public ProductViewHolder(View itemView) {
            super( itemView );
            textViewnom = itemView.findViewById(R.id.nomalumne);
            textViewcognom = itemView.findViewById(R.id.cognomalumne);
            imageView = itemView.findViewById( R.id.imagealumne );
            checkBox = itemView.findViewById(R.id.checkBox );
            itemView.setOnClickListener( this );
            itemView.setOnLongClickListener( this );

        }

        public void setItemClickListener(ItemClassListener itemClassListener){
            this.itemClassListener = itemClassListener;
        }

        @Override
        public void onClick(View v) {
            itemClassListener.onClick(v,getAdapterPosition(),false);
            int adapterPosition = getAdapterPosition();
            if (!itemStateArray.get(adapterPosition, false)) {
                checkBox.setChecked(true);
                itemStateArray.put(adapterPosition, true);
            }
            else  {
                checkBox.setChecked(false);
                itemStateArray.put(adapterPosition, false);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            itemClassListener.onClick( v,getAdapterPosition(),true);
            return true;
        }
    }

}

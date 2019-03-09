package com.example.a12524.experimentone_week6;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FoodAdapter<T> extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private List<listitem> foodlist;
    public FoodAdapter(List<listitem> _foodList){
        foodlist = _foodList;
    }
    static public class ViewHolder extends RecyclerView.ViewHolder {
        View foodView;
        TextView foodtitle;
        TextView foodname;

        public ViewHolder(View itemView){
            super(itemView);
            foodView = itemView;
            foodtitle = (TextView) itemView.findViewById(R.id.foodtitle);
            foodname = (TextView) itemView.findViewById(R.id.foodname);
        }
        static public ViewHolder get(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout,parent,false);
            final ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }
    }
    public int getItemCount()
    {
        return foodlist.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener){
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        final ViewHolder viewHolder = ViewHolder.get(parent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        listitem food = foodlist.get(position);
        holder.foodname.setText(food.getName());
        holder.foodtitle.setText(food.getTitle());
        if(mOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        if(mOnItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    return true;
                }
            });
        }
    }


}
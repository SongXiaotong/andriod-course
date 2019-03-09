package com.example.a12524.httpapi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a12524.httpapi.R;
import com.example.a12524.httpapi.model.repo;

import java.util.List;

public class RepoAdapter<T> extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
    private List<repo> list;

    public RepoAdapter(List<repo> _list){
        list = _list;
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView name;
        TextView id;
        TextView problem;
        TextView detail;

        public ViewHolder(View itemView){
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.proname);
            id = (TextView) itemView.findViewById(R.id.proid);
            problem = (TextView) itemView.findViewById(R.id.proproblem);
            detail = (TextView) itemView.findViewById(R.id.prodetail);
        }
        static public ViewHolder get(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo, parent,false);
            final ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }
    }

    public int getItemCount()
    {
        return list.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final ViewHolder viewHolder = ViewHolder.get(viewGroup);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
       // Log.e("123","123");
        final repo repo0 = list.get(i);
      //  Log.e("name", repo0.getName());
        holder.name.setText("项目名： "+ repo0.getName());
        holder.problem.setText("存在问题： "+String.valueOf(repo0.getOpen_issues()));
        holder.id.setText("项目id： " + repo0.getId());
        holder.detail.setText("项目描述： " + repo0.getDescription());
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            Log.e("321", "321");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
    }

}
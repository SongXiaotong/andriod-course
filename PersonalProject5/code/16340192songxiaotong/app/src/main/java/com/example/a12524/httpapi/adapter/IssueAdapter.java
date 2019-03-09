package com.example.a12524.httpapi.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a12524.httpapi.R;
import com.example.a12524.httpapi.model.issue;

import java.util.List;

public class IssueAdapter<T> extends RecyclerView.Adapter<IssueAdapter.ViewHolder> {
    private List<issue> list;

    public IssueAdapter(List<issue> _list){
        list = _list;
    }

    static public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView title;
        TextView state;
        TextView created_at;
        TextView body;

        public ViewHolder(View itemView){
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.isstitle);
            created_at = (TextView) itemView.findViewById(R.id.createtime);
            state = (TextView) itemView.findViewById(R.id.probstatus);
            body = (TextView) itemView.findViewById(R.id.probdetail);
        }
        static public ViewHolder get(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue, parent,false);
            final ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }
    }

    public int getItemCount()
    {
        return list.size();
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
        final issue issue0 = list.get(i);
        //  Log.e("name", repo0.getName());
        holder.title.setText("Title： "+ issue0.getTitle());
        holder.created_at.setText("创建时间： "+ issue0.getCreated_at());
        holder.state.setText("问题状态： " + issue0.getState());
        holder.body.setText("问题描述： " + issue0.getBody());

    }

}
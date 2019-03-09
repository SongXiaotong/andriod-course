package com.example.a12524.experimentone_week6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a12524.experimentone_week6.listitem;

import java.util.List;

public class CollectAdapter extends BaseAdapter {

    private Context context;
    private List<listitem> collectfoods;
    private int ID;
    public CollectAdapter(Context context, int ID, List<listitem>listitems) {
        this.ID = ID;
        this.context = context;
        collectfoods = listitems;
    }

    public List<listitem> getListItems() {
        return collectfoods;
    }

    public void setListItems(List<listitem> listItems) {
        this.collectfoods = listItems;
    }
    @Override
    public int getCount() {
        return collectfoods.size();
    }

    @Override
    public Object getItem(int position) {
        if(collectfoods==null)
        {
            return null;
        }
        return collectfoods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView;
        ListItemView myListItemView;
        if (view == null){
            myListItemView = new ListItemView();
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.layout3,parent,false);
            myListItemView.set_name((TextView) convertView
                    .findViewById(R.id.foodname));
            myListItemView.set_title((TextView) convertView
                    .findViewById(R.id.foodtitle));
            convertView.setTag(myListItemView);
        }
        else{
            convertView = view;
            myListItemView = (ListItemView) convertView.getTag();
        }
        myListItemView.get_name()
                .setText(collectfoods.get(position).getName());
        myListItemView.get_title().setText(collectfoods.get(position).getTitle());

        return convertView;
    }

    class ListItemView{
        private TextView _name;
        private TextView _title;

        public TextView get_name() {
            return _name;
        }

        public void set_name(TextView name) {
            this._name = name;
        }

        public TextView get_title() {
            return _title;
        }

        public void set_title(TextView title) {
            this._title = title;
        }
    }
}
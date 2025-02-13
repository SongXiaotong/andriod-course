package com.example.jason.finalproj.MyArticle;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jason.finalproj.R;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    protected Context mContext;
    protected int mLayoutid;
    protected List<Map<String, Object>> mData;
    protected List<Map<String, Bitmap>> mImage;
    private OnItemClickListener mOnItemClickListener = null;

    public ArticleAdapter (Context context, int layoutid, List<Map<String, Object>> datas , List<Map<String, Bitmap>> images) {
        mContext = context;
        mLayoutid = layoutid;
        mData = datas;
        mImage = images;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mtype;      //类型（比赛、项目、个人）
        public CircleImageView mphoto;    //头像
        public TextView mname;      //名字
        public ImageView mbadge;    //徽章
        public TextView mdate;      //日期
        public TextView mtitle;     //标题
        public TextView mbody;      //正文
        public ImageView mstate;    //状态（招募中、已结束）
        public ImageView mcollection;//收藏
        public ImageView mpraise;   //点赞
        View mView;     //布局视图

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mtype = (TextView) itemView.findViewById(R.id.article_item_type);
            mphoto = (CircleImageView) itemView.findViewById(R.id.article_item_photo);
            mname = (TextView) itemView.findViewById(R.id.article_item_name);
            mbadge = (ImageView) itemView.findViewById(R.id.article_item_badge);
            mdate = (TextView) itemView.findViewById(R.id.article_item_date);
            mtitle = (TextView) itemView.findViewById(R.id.article_item_title);
            mbody = (TextView) itemView.findViewById(R.id.article_item_body);
            mcollection = (ImageView) itemView.findViewById(R.id.article_item_collection);
            mstate = (ImageView) itemView.findViewById(R.id.article_item_state);
        }
        public static ViewHolder get(Context context, ViewGroup parent, int layoutId) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }
    }

    public interface OnItemClickListener {
        void OnClick(View view, int position);
        void LongClick(View view, int position);
        void AttentionOnClick(View view, int position);
    }
    public void setonItemClickListener (OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int ViewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutid);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //类型
        TextView type = holder.mtype;
        type.setText(mData.get(position).get("type").toString());
        //头像
        ImageView photo = holder.mphoto;
        photo.setImageBitmap(mImage.get(position).get("photo"));
        //名字
        TextView name = holder.mname;
        name.setText(mData.get(position).get("name").toString());
        //徽章
        ImageView badge = holder.mbadge;
        badge.setImageBitmap(mImage.get(position).get("badge"));
        //日期
        TextView date = holder.mdate;
        date.setText(mData.get(position).get("date").toString());
        //标题
        TextView title = holder.mtitle;
        title.setText(mData.get(position).get("title").toString());
        //正文
        TextView body = holder.mbody;
        body.setText(mData.get(position).get("body").toString());
        //状态
        ImageView state = holder.mstate;
        state.setImageBitmap(mImage.get(position).get("state"));
        //收藏
        ImageView collection = holder.mcollection;
        collection.setImageBitmap(mImage.get(position).get("collection"));

        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.OnClick(holder.itemView, holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick (View view) {
                    mOnItemClickListener.LongClick(holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });
            holder.mcollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.AttentionOnClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}

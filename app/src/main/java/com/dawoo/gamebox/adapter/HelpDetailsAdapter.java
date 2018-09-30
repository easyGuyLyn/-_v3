package com.dawoo.gamebox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.HelpDetailsBean;

import java.util.List;

/**
 * Created by jack on 18-3-25.
 */

public class HelpDetailsAdapter extends RecyclerView.Adapter<HelpDetailsAdapter.MyViewHolder> {
    Context context;
    List list;
    Integer layout;

    public HelpDetailsAdapter(Context context, List<HelpDetailsBean.ListBean> list, Integer layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public HelpDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater
                .from(context).inflate(layout, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(final HelpDetailsAdapter.MyViewHolder holder, int position) {
        if (list.get(position) instanceof HelpDetailsBean) {//判断当前实体是否是Parent的实例
            holder.child_name.setVisibility(View.GONE);
            holder.parent_name.setVisibility(View.VISIBLE);
            HelpDetailsBean parent = (HelpDetailsBean) list.get(position);
            holder.parent_name.setText(Html.fromHtml(parent.getList().get(position).getHelpTitle()));
        } else {//判断当前实体是否是Child的实例
            holder.parent_name.setVisibility(View.GONE);
            holder.child_name.setVisibility(View.VISIBLE);
            HelpDetailsBean.ListBean child = (HelpDetailsBean.ListBean) list.get(position);
            holder.child_name.setText(Html.fromHtml(child.getHelpContent()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView parent_name;
        private TextView child_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            parent_name = (TextView) itemView.findViewById(R.id.parent_name);
            child_name = (TextView) itemView.findViewById(R.id.child_name);
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickLitener(OnItemClickListener itemClickListener) {
        this.onItemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
        super.onBindViewHolder(holder, position, payloads);

    }

    /**
     * 添加所有child
     *
     * @param lists
     * @param position
     */
    public void addAllChild(List<?> lists, int position) {
        list.addAll(position, lists);
        notifyItemRangeInserted(position, lists.size());
    }

    /**
     * 删除所有child
     *
     * @param position
     * @param itemnum
     */
    public void deleteAllChild(int position, int itemnum) {
        for (int i = 0; i < itemnum; i++) {
            list.remove(position);
        }
        notifyItemRangeRemoved(position, itemnum);
    }
}
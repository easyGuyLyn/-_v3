package com.dawoo.gamebox.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.GameNotice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by benson on 18-1-15.
 */

public class GameNoticeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private View.OnClickListener mListener;

    private List<GameNotice.ListBean> mList = new ArrayList<>();


    public GameNoticeAdapter(Context context, View.OnClickListener listener) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GameNoticeViewHolder viewHolder = new GameNoticeViewHolder(mLayoutInflater.inflate(R.layout.message_list_item_game_notice, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List list) {
        mList.clear();
        mList.addAll(list);
        notifyItemChanged(0, list.size());
    }

    public void loadMoreData(List list) {
        int index = mList.size();
        mList.addAll(list);
        notifyItemChanged(index, list.size());
    }

    /**
     * 游戏公告
     */
    class GameNoticeViewHolder extends BaseViewHolder {
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.game_type)
        TextView mGameType;
        @BindView(R.id.time)
        TextView mTime;

        public GameNoticeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindView() {
            super.onBindView();

            GameNotice.ListBean bean = mList.get(getAdapterPosition());
            if(bean != null) {
                mContent.setText(bean.getContext());
                mGameType.setText(bean.getGameName());
                mTime.setText(DateTool.convert2String(new Date(bean.getPublishTime()),DateTool.FMT_DATE_TIME));
            }
        }
    }

}

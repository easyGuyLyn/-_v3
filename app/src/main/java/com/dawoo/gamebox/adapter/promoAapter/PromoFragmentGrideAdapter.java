package com.dawoo.gamebox.adapter.promoAapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dawoo.coretool.util.activity.DensityUtil;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.ActivityType;

import java.util.List;

/**
 * Created by jack on 18-1-23.
 */

public class PromoFragmentGrideAdapter extends BaseAdapter {
    private Context mcontext;
    private List<ActivityType> activityTypes;
    private int location;

    public PromoFragmentGrideAdapter(Context context, List<ActivityType> activityType) {
        this.mcontext = context;
        this.activityTypes = activityType;

    }

    @Override
    public int getCount() {
        return activityTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return activityTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderTime holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mcontext);
            convertView = inflater.inflate(R.layout.load_gradedate_ltem_view, null);
            holder = new ViewHolderTime();
            holder.week = (TextView) convertView.findViewById(R.id.load_text);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolderTime) convertView.getTag();
        }

        if (location == position) {
            holder.week.setTextColor(mcontext.getResources().getColor(R.color.white));
            holder.week.setSelected(true);
        } else {
            holder.week.setTextColor(mcontext.getResources().getColor(R.color.promo_text_color));
            holder.week.setSelected(false);
        }

        int size = activityTypes.get(position).getActivityTypeName().length();
        if (size <= 5) {
            holder.week.setText(activityTypes.get(position).getActivityTypeName());
        } else if (5 < size && size <= 10) {
            setTextSize(12, holder, position);
        } else if (10 < size && size <= 12) {
            setTextSize(10, holder, position);
        } else {
            setTextSize(10, holder, position);
        }

        return convertView;
    }

    public class ViewHolderTime {
        public TextView week;
    }

    public void setSeclection(int position) {
        location = position;
    }

    private void setTextSize(int size, ViewHolderTime holder, int position) {
        holder.week.setTextSize(TypedValue.COMPLEX_UNIT_PX, DensityUtil.sp2px(mcontext, size));
        holder.week.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        holder.week.setPadding(8, 0, 0, 0);
        holder.week.setText(activityTypes.get(position).getActivityTypeName());
    }
}

package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dawoo.gamebox.R;
import com.dawoo.gamebox.view.view.wheel.OnWheelChangedListener;
import com.dawoo.gamebox.view.view.wheel.WheelView;
import com.dawoo.gamebox.view.view.wheel.adapters.NumericWheelAdapter;

import java.util.Calendar;


/**
 * Created by rain on 18-3-28.
 */

public class DateTimePickerDailog extends Dialog {
    private WheelView year_wl, month_wl, date_wl, hour_wl, min_wl;
    NumericWheelAdapter mYearAdapter, mMonthAdapter, mDayAdapter, mHourAdapter, mMinuteAdapter;
    private TextView sureBT, cancleBT;

    public DateTimePickerDailog(Context context) {
        super(context, R.style.CustomDialogStyle);
        initView();
    }

    void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_simple_date_time_layout);
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        win.setGravity(Gravity.BOTTOM);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        year_wl = findViewById(R.id.year_wl);
        month_wl = findViewById(R.id.month_wl);
        date_wl = findViewById(R.id.date_wl);
        hour_wl = findViewById(R.id.hour_wl);
        min_wl = findViewById(R.id.min_wl);
        cancleBT = findViewById(R.id.cancle_bt);
        sureBT = findViewById(R.id.sure_bt);
        cancleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initDatas();

    }

    void initDatas() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int year = cal.get(Calendar.YEAR);
        mYearAdapter = new NumericWheelAdapter(getContext(), year - 6, year + 2);
        year_wl.setViewAdapter(mYearAdapter);
        year_wl.setCyclic(true);
        year_wl.setCurrentItem(6);

        mMonthAdapter = new NumericWheelAdapter(getContext(), 01, 12);
        month_wl.setViewAdapter(mMonthAdapter);
        month_wl.setCyclic(true);
        int month = cal.get(Calendar.MONTH);
        month_wl.setCurrentItem(month);
        initDays();
        int day = cal.get(Calendar.DATE);
        date_wl.setCurrentItem(day);

        mHourAdapter = new NumericWheelAdapter(getContext(), 00, 23);
        hour_wl.setCyclic(true);
        hour_wl.setViewAdapter(mHourAdapter);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        hour_wl.setCurrentItem(hour);

        mMinuteAdapter = new NumericWheelAdapter(getContext(), 00, 59);
        min_wl.setViewAdapter(mMinuteAdapter);
        min_wl.setCyclic(true);
        int min = cal.get(Calendar.MINUTE);
        min_wl.setCurrentItem(min);

        year_wl.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                initDays();
            }
        });
        month_wl.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                initDays();
            }
        });
    }

    void initDays() {
        int year = Integer.valueOf(mYearAdapter.getItemText(year_wl.getCurrentItem()).toString());
        int mon = Integer.valueOf(mMonthAdapter.getItemText(month_wl.getCurrentItem()).toString());
        mDayAdapter = new NumericWheelAdapter(getContext(), 1, getMaxDay(year, mon));
        date_wl.setViewAdapter(mDayAdapter);
        date_wl.setCyclic(true);
    }

    public String getTime() {
        String year = mYearAdapter.getItemText(year_wl.getCurrentItem()).toString();
        String month = mMonthAdapter.getItemText(month_wl.getCurrentItem()).toString();
        String day = mDayAdapter.getItemText(date_wl.getCurrentItem()).toString();
        String hour = mHourAdapter.getItemText(hour_wl.getCurrentItem()).toString();
        String min = mMinuteAdapter.getItemText(min_wl.getCurrentItem()).toString();
        if(month.length()==1){
            month="0"+month;
        }
        if(day.length()==1){
            day="0"+day;
        }
        if(hour.length()==1){
            hour="0"+hour;
        }
        if(min.length()==1){
            min="0"+min;
        }
        StringBuffer sb = new StringBuffer();
        sb.append(year);
        sb.append("-");
        sb.append(month);
        sb.append("-");
        sb.append(day);
        sb.append(" ");
        sb.append(hour);
        sb.append(":");
        sb.append(min);
        return sb.toString();
    }

    public void setSureTimeClicked(View.OnClickListener onClickListener) {
        if (onClickListener == null) {
            return;
        }
        sureBT.setOnClickListener(onClickListener);

    }

    public int getMaxDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}

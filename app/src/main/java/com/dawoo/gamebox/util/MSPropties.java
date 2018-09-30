package com.dawoo.gamebox.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.dawoo.coretool.util.activity.DensityUtil;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.ActivityType;
import com.dawoo.gamebox.bean.ActivityTypeList;
import com.dawoo.gamebox.view.activity.more.ProblemsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 18-1-28.
 */

public class MSPropties {
    /**
     * 加載头部带全部的type
     *
     * @param activityType
     * @param context
     * @return
     */
    public static List<ActivityType> getALLTypeDate(List<ActivityType> activityType, Context context) {
        List<ActivityType> AllType = new ArrayList<>();
        AllType.add(new ActivityType("", context.getString(R.string.all_type_date)));
        for (int i = 0; i < activityType.size(); i++) {
            ActivityType activityType1 = new ActivityType();
            activityType1.setActivityTypeName(activityType.get(i).getActivityTypeName());
            activityType1.setActivityKey(activityType.get(i).getActivityKey());
            AllType.add(activityType1);
        }

        return AllType;
    }

    /**
     * 动态设置头部的高度
     *
     * @param activityTypes
     * @param view
     */
    public static void height(List<ActivityType> activityTypes, View view, Context context) {
        if (activityTypes.size() > 0 && activityTypes.size() <= 4) {
            setHight(10+28, view, context);
        } else if (5 <= activityTypes.size() && activityTypes.size() <= 8) {
            setHight((10+28)*2, view, context);
        } else if (9 <= activityTypes.size() && activityTypes.size() <= 12) {
            setHight((10+28)*3, view, context);
        } else {
            setHight((10+28)*4, view, context);
        }


    }

    /**
     * 设置高度
     *
     * @param height
     * @param view
     */
    public static void setHight(int height, View view, Context context) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = DensityUtil.dp2px(context, height);
        view.setLayoutParams(linearParams);
    }


    /**
     * 根据activitytypes的长度创建activityTypeLists的空对象
     *
     * @param activityTypes
     * @param activityTypeLists
     */
    public static void activityTypeLists(List<ActivityType> activityTypes, List<List<ActivityTypeList.ListBean>> activityTypeLists) {
        for (int i = 0; i < activityTypes.size(); i++) {
            List<ActivityTypeList.ListBean> listBeanlist = new ArrayList<>();
            activityTypeLists.add(listBeanlist);
        }
    }

    public static void startActivity(Class<ProblemsActivity> aClass) {
        BoxApplication.getContext().startActivity(new Intent(BoxApplication.getContext(), aClass.getClass()));
    }
}

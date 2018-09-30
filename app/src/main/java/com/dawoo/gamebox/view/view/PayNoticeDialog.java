package com.dawoo.gamebox.view.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dawoo.coretool.util.math.BigDemicalUtil;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.adapter.HomeAdapter.SalesAdapter;
import com.dawoo.gamebox.bean.payInfo.PayItemBean;
import com.dawoo.gamebox.bean.payInfo.PopPayBean;
import com.dawoo.gamebox.bean.payInfo.SaleBean;
import com.hwangjr.rxbus.RxBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rain on 18-3-22.
 */

public class PayNoticeDialog extends Dialog implements View.OnClickListener {

    RecyclerView offersRV;

    Button sureBt;

    ImageView closeIV;

    TextView moneyTV;

    TextView otherMoneyTV;
    TextView offersTv;
    PostInfoInterface postInfoInterface;
    int selected = -1;
    SalesAdapter salesAdapter;

    public PayNoticeDialog(@NonNull Context context) {
        super(context, R.style.CustomDialogStyle);
        initView();

    }

    void initView() {
        setContentView(R.layout.dialog_pay_notice);
        offersRV = findViewById(R.id.offers_rv);
        sureBt = findViewById(R.id.sure_bt);
        closeIV = findViewById(R.id.close_iv);
        moneyTV = findViewById(R.id.money_tv);
        otherMoneyTV = findViewById(R.id.other_money_tv);
        offersTv = findViewById(R.id.offers_tv);

        setCancelable(false);
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        offersRV.setLayoutManager(manager);
        offersRV.addItemDecoration(new DashlineItemDivider());
        salesAdapter = new SalesAdapter(R.layout.item_sales_layout);
        offersRV.setAdapter(salesAdapter);
        salesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == selected) {
                    return;
                }
                salesAdapter.setSelected(position);
                selected = position;
            }
        });
        sureBt.setOnClickListener(this);
        closeIV.setOnClickListener(this);
    }

    public void setData(double money, PopPayBean popPayBean) {
        moneyTV.setText(BigDemicalUtil.moneyFormat(money));
        otherMoneyTV.setText(popPayBean.getMsg());
        if (popPayBean.getSales() == null) {
            popPayBean.setSales(new ArrayList<SaleBean>());
        }
//        if (popPayBean.getSales().isEmpty()) {
//            SaleBean saleBean = new SaleBean();
//            saleBean.setId(0);
//            saleBean.setActivityName("不参与优惠");
//            popPayBean.getSales().add(0, saleBean);
//        }

        if (popPayBean.getSales().isEmpty()||popPayBean.getSales().size()<0) {
            offersTv.setVisibility(View.GONE);
            offersRV.setVisibility(View.GONE);
        } else {
            offersTv.setVisibility(View.VISIBLE);
            offersRV.setVisibility(View.VISIBLE);
        }
        salesAdapter.setNewData(popPayBean.getSales());
        selected = 0;
        salesAdapter.setSelected(selected);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sure_bt:
                if (postInfoInterface == null) {
                    return;
                }
                postInfoInterface.postPayInfo();
                dismiss();
                break;
            case R.id.close_iv:
                dismiss();
                break;
        }
    }

    public interface PostInfoInterface {
        void postPayInfo();
    }

    public void setSureOnlick(PostInfoInterface postInfoInterface) {
        this.postInfoInterface = postInfoInterface;
    }

    public long getId() {
        if (selected == -1) {
            return 0;
        } else if (salesAdapter.getItemCount() == 0) {
            return 0;
        } else {
            return ((SaleBean) salesAdapter.getData().get(selected)).getId();
        }
    }


    public class DashlineItemDivider extends RecyclerView.ItemDecoration {


        public void onDrawOver(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < (childCount - 1); i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                //以下计算主要用来确定绘制的位置
                final int top = child.getBottom() + params.bottomMargin;

                //绘制虚线
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.text_color_gray_999999));
                Path path = new Path();
                path.moveTo(left, top);
                path.lineTo(right, top);
                PathEffect effects = new DashPathEffect(new float[]{3, 3, 3, 3}, 0);//此处单位是像素不是dp  注意 请自行转化为dp
                paint.setPathEffect(effects);
                c.drawPath(path, paint);
            }
        }
    }
}

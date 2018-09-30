package com.dawoo.gamebox.adapter.HomeAdapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dawoo.coretool.util.date.DateTool;
import com.dawoo.gamebox.R;
import com.dawoo.gamebox.bean.AuditBean;
import com.dawoo.gamebox.util.SharePreferenceUtil;

public class AuditAdapter extends BaseQuickAdapter {

    private String currencySign;
    private Context context;

    public AuditAdapter(Context context ,int layoutResId, String currencySign) {
        super(layoutResId);
        this.currencySign = currencySign;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        AuditBean.WithdrawAudit withdrawAudit = (AuditBean.WithdrawAudit) item;
        //存款时间
        if (!TextUtils.isEmpty(withdrawAudit.getCreateTime()+"")){
            String mTimeZone = SharePreferenceUtil.getTimeZone(context);
            long time = DateTool.convertTimeInMillisWithTimeZone(withdrawAudit.getCreateTime(), mTimeZone);
            helper.setText(R.id.tv_deposit_time, DateTool.getTimeFromLong(DateTool.FMT_DATE_TIME, time));
        }

        //存款金额
        if (!TextUtils.isEmpty(withdrawAudit.getRechargeAmount())){
            helper.setText(R.id.tv_deposit_money,currencySign+withdrawAudit.getRechargeAmount());
        }

        //存款稽核
        if (!TextUtils.isEmpty(withdrawAudit.getRechargeAudit())&&!TextUtils.isEmpty(withdrawAudit.getRechargeRemindAudit())){
            helper.setText(R.id.tv_audit_point,withdrawAudit
                    .getRechargeRemindAudit()+"/"+withdrawAudit.getRechargeAudit());
        }
        //剩余存款稽核
        if (!TextUtils.isEmpty(withdrawAudit.getRechargeRemindAudit())){

        }
        //行政费用，如果为0显示通过，其他直接展示费用
        if (withdrawAudit.getRechargeFee()!=null&&!"null".equals(withdrawAudit.getRechargeFee())){
            if (withdrawAudit.getRechargeFee() == 0){
                helper.setText(R.id.tv_administrative_costs,"通过");
            }else {
                helper.setText(R.id.tv_administrative_costs,currencySign+withdrawAudit.getRechargeFee());
            }
        }

        //优惠金额
        if (!TextUtils.isEmpty(withdrawAudit.getFavorableAmount())){
            helper.setText(R.id.tv_discount_amont,currencySign+withdrawAudit.getFavorableAmount());
        }

        // //优惠稽核

        if (!TextUtils.isEmpty(withdrawAudit.getFavorableAudit())&&!TextUtils.isEmpty(withdrawAudit.getFavorableRemindAudit())){
            helper.setText(R.id.tv_discount_audit_point,withdrawAudit.getFavorableRemindAudit()+"/"+withdrawAudit.getFavorableAudit());
        }

        //优惠扣除，如果为0显示通过，其他直接展示费用
        if (withdrawAudit.getFavorableFee()!=null&&!"null".equals(withdrawAudit.getFavorableFee())){
            if (withdrawAudit.getFavorableFee() == 0){
                helper.setText(R.id.tv_discount_deducted,"通过");
            }else {
                helper.setText(R.id.tv_discount_deducted,currencySign+withdrawAudit.getFavorableFee());
            }
        }

    }
}

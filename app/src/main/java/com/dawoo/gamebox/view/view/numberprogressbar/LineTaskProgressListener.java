package com.dawoo.gamebox.view.view.numberprogressbar;

import com.dawoo.gamebox.bean.line.LineErrorDialogBean;
import com.dawoo.gamebox.util.line.LineTaskBaseListener;

/**
 * Created by archar on 15-4-23.
 * SpashAcitivity的线路任务UI回调器
 */
public interface LineTaskProgressListener extends LineTaskBaseListener {

    void onProgressBarChange(int current, int max);

    void onErrorSimpleReason(String result);

    void onErrorComplexReason(LineErrorDialogBean lineErrorDialogBean);

    void onSpalshGetLineSuccess(String domain, String ip, LineTaskProgressListener lineTaskProgressListener);
}

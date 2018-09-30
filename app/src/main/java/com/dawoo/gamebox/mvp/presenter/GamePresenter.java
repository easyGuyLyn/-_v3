package com.dawoo.gamebox.mvp.presenter;

import android.content.Context;

import com.dawoo.gamebox.mvp.model.game.GameModel;
import com.dawoo.gamebox.mvp.model.record.RecordModel;
import com.dawoo.gamebox.mvp.view.IBaseView;
import com.dawoo.gamebox.mvp.view.ICasinoGameListView;
import com.dawoo.gamebox.mvp.view.INoteRecordView;
import com.dawoo.gamebox.net.rx.ProgressSubscriber;

import rx.Subscription;


/**
 * 游戏相关
 * Created by benson on 18-01-08.
 */

public class GamePresenter<T extends IBaseView> extends BasePresenter {

    private final Context mContext;
    private T mView;
    private final GameModel mModel;


    public GamePresenter(Context context, T mView) {
        super(context, mView);

        mContext = context;
        this.mView = mView;
        mModel = new GameModel();
    }


    /**
     * 获取电子游戏列表
     */
    public void getCasinoGameList(int apiId, int apiTypeId, int pageNumber, int pageSize, String name) {
        Subscription subscription = mModel.getCasinoGameList(new ProgressSubscriber(o ->
                        ((ICasinoGameListView) mView).onLoadResult(o), mContext, false),
                apiId,
                apiTypeId,
                pageNumber,
                pageSize,
                name);
        subList.add(subscription);
    }

    /**
     * 获取电子游戏列表
     */
    public void getCasinoGameList(int apiId, int apiTypeId, int pageNumber, int pageSize, String name, String tagId) {
        Subscription subscription = mModel.getCasinoGameList(new ProgressSubscriber(o ->
                        ((ICasinoGameListView) mView).onLoadResult(o), mContext, false),
                apiId,
                apiTypeId,
                pageNumber,
                pageSize,
                name,
                tagId);
        subList.add(subscription);
    }

    /**
     * 加载更多电子游戏列表
     */
    public void loadMoreCasinoGameList(int apiId, int apiTypeId, int pageNumber, int pageSize, String name) {
        Subscription subscription = mModel.getCasinoGameList(new ProgressSubscriber(o ->
                        ((ICasinoGameListView) mView).loadMoreData(o), mContext),
                apiId,
                apiTypeId,
                pageNumber,
                pageSize,
                name);
        subList.add(subscription);
    }

    /**
     * 加载更多电子游戏列表
     */
    public void loadMoreCasinoGameList(int apiId, int apiTypeId, int pageNumber, int pageSize, String name, String tagId) {
        Subscription subscription = mModel.getCasinoGameList(new ProgressSubscriber(o ->
                        ((ICasinoGameListView) mView).loadMoreData(o), mContext),
                apiId,
                apiTypeId,
                pageNumber,
                pageSize,
                name,
                tagId);
        subList.add(subscription);
    }

    /**
     * 获取游戏链接
     */
    public void getGameLink(int apiId, int apiTypeId, int gameId, String gameCode) {
        Subscription subscription = mModel.getGameLink(new ProgressSubscriber(o ->
                        ((ICasinoGameListView) mView).onLoadGameLink(o), mContext),
                apiId,
                apiTypeId,
                gameId,
                gameCode);
        subList.add(subscription);
    }

    /**
     * 获取游戏类别
     */
    public void getGameTag(int apiId, int apiTypeId) {
        Subscription subscription = mModel.getGameTag(new ProgressSubscriber(o ->
                        ((ICasinoGameListView) mView).onLoadGameTagResult(o), mContext, false),
                apiId,
                apiTypeId);
        subList.add(subscription);
    }

    //中文转Unicode
    public String gbEncoding(final String gbString) {   //gbString = "测试"
        char[] utfBytes = gbString.toCharArray();   //utfBytes = [测, 试]
        String unicodeBytes = "";
        for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
            String hexB = Integer.toHexString(utfBytes[byteIndex]);   //转换为16进制整型字符串
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        System.out.println("unicodeBytes is: " + unicodeBytes);
        return unicodeBytes;
    }

    @Override
    public void onDestory() {
        super.onDestory();
    }
}

package com.dawoo.gamebox.mvp.model.game;

import android.support.annotation.Nullable;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by benson on 18-1-8.
 */

public interface IGameModel {


    Subscription getCasinoGameList(Subscriber subscriber, int apiId, int apiTypeId, int PageNumber, int PageSize, String name);

    Subscription getCasinoGameList(Subscriber subscriber, int apiId, int apiTypeId, int PageNumber, int PageSize, String name,String tagId);

    Subscription getGameLink(Subscriber subscriber, int apiId, int apiTypeId, @Nullable int gameId, @Nullable String gameCode);

    Subscription getGameLink(Subscriber subscriber, int apiId, int apiTypeId);

    Subscription getGameTag(Subscriber subscriber,int apiId, int apiTypeId);
}

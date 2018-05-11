package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.ZxzDetailsPresenter;
import com.sx.cfsz.cfsz.ui.xrw.activity.ZxzDetailsActivity;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/5/2 0002    13:33      */

@Module
public class ZxzDetailsModule {
    ZxzDetailsActivity activity;

    public ZxzDetailsModule(ZxzDetailsActivity activity) {
        this.activity = activity;
    }

    @Provides
    ZxzDetailsPresenter providesZxzDetailsPresenter(){
        return new ZxzDetailsPresenter(activity);
    }
}

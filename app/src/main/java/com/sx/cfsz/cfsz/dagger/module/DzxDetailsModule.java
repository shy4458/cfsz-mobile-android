package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.DzxDetailsPresenter;
import com.sx.cfsz.cfsz.ui.xrw.activity.DzxDetailsActivity;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/4/23 0023    16:19      */

@Module
public class DzxDetailsModule {

    DzxDetailsActivity activity;

    public DzxDetailsModule(DzxDetailsActivity activity) {
        this.activity = activity;
    }

    @Provides
    DzxDetailsPresenter providesDzxDetailsPresenter(){
        return new DzxDetailsPresenter(activity);
    }
}

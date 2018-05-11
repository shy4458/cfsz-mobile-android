package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.YwcDetailsPresenter;
import com.sx.cfsz.cfsz.presenter.YwcFragmentPersenter;
import com.sx.cfsz.cfsz.ui.xrw.activity.YwcDetailsActivity;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/5/4 0004    13:00      */

@Module
public class YwcDetailsModule {

    YwcDetailsActivity activity;

    public YwcDetailsModule(YwcDetailsActivity activity) {
        this.activity = activity;
    }

    @Provides
    YwcDetailsPresenter providesYwcDetailsPresenter(){
        return new YwcDetailsPresenter (activity);
    }
}

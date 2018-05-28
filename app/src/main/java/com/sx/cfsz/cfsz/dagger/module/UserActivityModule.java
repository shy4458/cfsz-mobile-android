package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.UserActivityPresenter;
import com.sx.cfsz.cfsz.ui.xrw.activity.UserActivity;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/5/22 0022    9:22      */

@Module
public class UserActivityModule {

    UserActivity activity;

    public UserActivityModule(UserActivity activity) {
        this.activity = activity;
    }

    @Provides
    UserActivityPresenter providesUserActivityPresenter(){
        return new UserActivityPresenter(activity);
    }

}

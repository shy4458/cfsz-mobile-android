package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.MainActivityPresenter;
import com.sx.cfsz.cfsz.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/5/20 0020    14:41      */
@Module
public class MainActivityModule {

    MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    MainActivityPresenter providesMainActivityPresenter(){
        return new MainActivityPresenter(activity);
    }
}

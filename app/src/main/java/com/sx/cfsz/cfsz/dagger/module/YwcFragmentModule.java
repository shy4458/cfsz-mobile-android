package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.YwcFragmentPersenter;
import com.sx.cfsz.cfsz.ui.xrw.fragment.YwcFragment;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/4/19 0019    13:14      */

@Module
public class YwcFragmentModule {

    YwcFragment fragment;

    public YwcFragmentModule(YwcFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    YwcFragmentPersenter providesYwcFragmentPersenter(){
        return new YwcFragmentPersenter(fragment);
    }
}

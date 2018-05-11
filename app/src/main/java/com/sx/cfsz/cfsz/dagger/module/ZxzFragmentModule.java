package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.ZxzFragmentPresenter;
import com.sx.cfsz.cfsz.ui.xrw.fragment.ZxzFragment;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/4/19 0019    11:11      */

@Module
public class ZxzFragmentModule {

    ZxzFragment fragment;

    public ZxzFragmentModule(ZxzFragment fragment) {
        this.fragment = fragment;
    }
    @Provides
    ZxzFragmentPresenter providesZxzFragmentPresenter(){
        return new ZxzFragmentPresenter(fragment);
    }
}

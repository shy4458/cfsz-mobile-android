package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.DzxFragmentPresenter;
import com.sx.cfsz.cfsz.ui.xrw.fragment.DzxFragment;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/4/19 0019    10:36      */

@Module
public class DzxFragmentModule {

    DzxFragment fragment;

    public DzxFragmentModule(DzxFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    DzxFragmentPresenter providesDzxFragemtnPresenter(){
        return new DzxFragmentPresenter(fragment);
    }
}

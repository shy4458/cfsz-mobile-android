package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.TjfxFragmentPresenter;
import com.sx.cfsz.cfsz.ui.tjfx.TjfxFragment;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/5/8 0008    16:13      */
@Module
public class TjfxFragmentModule {

    TjfxFragment fragment;

    public TjfxFragmentModule(TjfxFragment fragment) {
        this.fragment = fragment;
    }
    @Provides
    TjfxFragmentPresenter providesTjfxFragmetnPresenter(){
        return new TjfxFragmentPresenter(fragment);
    }
}

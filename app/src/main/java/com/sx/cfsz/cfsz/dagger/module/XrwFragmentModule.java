package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.XrwFragmentPresenter;
import com.sx.cfsz.cfsz.ui.xrw.fragment.XrwFragment;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/4/18 0018    15:51      */

@Module
public class XrwFragmentModule {

    XrwFragment fragment;

    public XrwFragmentModule(XrwFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    XrwFragmentPresenter providesXrwFragmentPresenter(){
        return new XrwFragmentPresenter(fragment);
    }
}

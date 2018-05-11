package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.SearchActivityPresenter;
import com.sx.cfsz.cfsz.ui.xrw.activity.SearchActivity;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/4/25 0025    14:39      */

@Module
public class SearchActivityModule {

    SearchActivity activity;

    public SearchActivityModule(SearchActivity activity) {
        this.activity = activity;
    }
    @Provides
    SearchActivityPresenter providesSearchActivityPresenter(){
        return new SearchActivityPresenter(activity);
    }

}

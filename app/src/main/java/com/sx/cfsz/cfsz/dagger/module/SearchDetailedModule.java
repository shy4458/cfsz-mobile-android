package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.SearchDetailePresenter;
import com.sx.cfsz.cfsz.ui.xrw.activity.SearchDetailedActivity;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/6/7 0007    9:46      */

@Module
public class SearchDetailedModule {

    SearchDetailedActivity activity;

    public SearchDetailedModule(SearchDetailedActivity activity) {
        this.activity = activity;
    }

    @Provides
    SearchDetailePresenter providesSearchDetailePresenter(){
        return new SearchDetailePresenter(activity);
    }
}

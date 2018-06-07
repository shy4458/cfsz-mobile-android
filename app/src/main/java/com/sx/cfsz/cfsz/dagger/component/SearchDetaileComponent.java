package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.SearchDetailedModule;
import com.sx.cfsz.cfsz.ui.xrw.activity.SearchDetailedActivity;

import dagger.Component;

/***       Author  shy
 *         Time   2018/6/7 0007    9:47      */

@Component(modules = SearchDetailedModule.class)
public interface SearchDetaileComponent {

    void in(SearchDetailedActivity activity);

}

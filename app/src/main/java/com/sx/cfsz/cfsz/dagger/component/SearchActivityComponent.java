package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.SearchActivityModule;
import com.sx.cfsz.cfsz.ui.xrw.activity.SearchActivity;

import dagger.Component;

/***       Author  shy
 *         Time   2018/4/25 0025    14:42      */

@Component (modules = SearchActivityModule.class)
public interface SearchActivityComponent {

    void in(SearchActivity activity);

}

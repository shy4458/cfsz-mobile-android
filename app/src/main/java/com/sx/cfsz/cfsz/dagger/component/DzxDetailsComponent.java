package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.DzxDetailsModule;
import com.sx.cfsz.cfsz.ui.xrw.activity.DzxDetailsActivity;

import dagger.Component;

/***       Author  shy
 *         Time   2018/4/23 0023    16:20      */


@Component(modules = DzxDetailsModule.class)
public interface DzxDetailsComponent {

    void in(DzxDetailsActivity activity);

}

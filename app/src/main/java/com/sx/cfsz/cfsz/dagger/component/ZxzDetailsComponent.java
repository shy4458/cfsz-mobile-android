package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.ZxzDetailsModule;
import com.sx.cfsz.cfsz.ui.xrw.activity.ZxzDetailsActivity;

import dagger.Component;

/***       Author  shy
 *         Time   2018/5/2 0002    13:34      */

@Component (modules = ZxzDetailsModule.class)
public interface ZxzDetailsComponent {
    void in(ZxzDetailsActivity activity);
}

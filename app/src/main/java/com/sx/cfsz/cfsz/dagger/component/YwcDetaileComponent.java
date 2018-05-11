package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.YwcDetailsModule;
import com.sx.cfsz.cfsz.ui.xrw.activity.YwcDetailsActivity;

import dagger.Component;

/***       Author  shy
 *         Time   2018/5/4 0004    13:02      */

@Component (modules = YwcDetailsModule.class)
public interface YwcDetaileComponent {

    void in(YwcDetailsActivity activity);

}

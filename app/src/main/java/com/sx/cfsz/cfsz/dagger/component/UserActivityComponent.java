package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.UserActivityModule;
import com.sx.cfsz.cfsz.ui.xrw.activity.UserActivity;

import dagger.Component;

/***       Author  shy
 *         Time   2018/5/22 0022    9:24      */
@Component (modules = UserActivityModule.class)
public interface UserActivityComponent {

    void in(UserActivity activity);

}

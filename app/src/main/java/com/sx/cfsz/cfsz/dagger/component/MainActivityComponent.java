package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.MainActivityModule;
import com.sx.cfsz.cfsz.ui.MainActivity;

import dagger.Component;

/***       Author  shy
 *         Time   2018/5/20 0020    14:42      */


@Component (modules = MainActivityModule.class)
public interface MainActivityComponent {

    void in(MainActivity activity);
}

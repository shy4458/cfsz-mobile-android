package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.YwcFragmentModule;
import com.sx.cfsz.cfsz.ui.xrw.fragment.YwcFragment;

import dagger.Component;

/***       Author  shy
 *         Time   2018/4/19 0019    13:15      */


@Component(modules = YwcFragmentModule.class)
public interface YwcFragmentComponent {

    void in(YwcFragment fragment);

}

package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.XrwFragmentModule;
import com.sx.cfsz.cfsz.ui.xrw.fragment.XrwFragment;

import dagger.Component;

/***       Author  shy
 *         Time   2018/4/18 0018    15:53      */

@Component(modules = XrwFragmentModule.class)
public interface XrwFragmentComponent {

    void in(XrwFragment fragment);

}

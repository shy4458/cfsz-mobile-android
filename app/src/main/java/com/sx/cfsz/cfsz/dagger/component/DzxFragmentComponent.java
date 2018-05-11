package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.DzxFragmentModule;
import com.sx.cfsz.cfsz.ui.xrw.fragment.DzxFragment;

import dagger.Component;

/***       Author  shy
 *         Time   2018/4/19 0019    10:38      */

@Component(modules = DzxFragmentModule.class)
public interface DzxFragmentComponent {

    void in(DzxFragment fragment);

}

package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.ZxzFragmentModule;
import com.sx.cfsz.cfsz.ui.xrw.fragment.ZxzFragment;

import dagger.Component;
import dagger.Module;

/***       Author  shy
 *         Time   2018/4/19 0019    13:10      */

@Component (modules = ZxzFragmentModule.class)
public interface ZxzFragmentComponent {
    void in(ZxzFragment fragment);

}

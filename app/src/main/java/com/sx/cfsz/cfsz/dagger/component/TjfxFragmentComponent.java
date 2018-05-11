package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.TjfxFragmentModule;
import com.sx.cfsz.cfsz.ui.tjfx.TjfxFragment;

import dagger.Component;

/***       Author  shy
 *         Time   2018/5/8 0008    16:15      */

@Component(modules = TjfxFragmentModule.class)
public interface TjfxFragmentComponent {

    void in(TjfxFragment fragment);

}

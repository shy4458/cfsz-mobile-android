package com.sx.cfsz.cfsz.dagger.component;

import com.sx.cfsz.cfsz.dagger.module.LoginActivityModule;
import com.sx.cfsz.cfsz.ui.LoginActivity;

import dagger.Component;

/***       Author  shy
 *         Time   2018/4/17 0017    14:00      */
@Component(modules = LoginActivityModule.class)
public interface LoginComponent {

    void in(LoginActivity activity);

}

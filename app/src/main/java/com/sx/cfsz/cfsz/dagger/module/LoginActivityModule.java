package com.sx.cfsz.cfsz.dagger.module;

import com.sx.cfsz.cfsz.presenter.LoginPresenter;
import com.sx.cfsz.cfsz.ui.LoginActivity;

import dagger.Module;
import dagger.Provides;

/***       Author  shy
 *         Time   2018/4/17 0017    13:57      */
@Module
public class LoginActivityModule {

    LoginActivity activity;

    public LoginActivityModule(LoginActivity activity) {
        this.activity = activity;
    }

    @Provides
    LoginPresenter providesLoginPresenter(){
        return new LoginPresenter(activity);
    }
}

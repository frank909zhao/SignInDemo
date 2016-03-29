package com.frank.signindemo;

/**
 * Created by frank on 2016/3/29.
 */
public interface LoginCallback {

        //登录成功
        public void onSuccessed();
        //登陆失败
        public void onFailed();
        //登录异常
        public void onError();
}

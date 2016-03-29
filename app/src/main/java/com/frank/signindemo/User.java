package com.frank.signindemo;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

public class User {

    private String username;
    private String pwd;
    //正确的用户名和密码
    private static final String USER = "frank";
    private static final String PWD = "123456";

    private static final int CODE_OK = 0;
    private static final int CODE_FAILED = 1;
    private static final int CODE_ERROR = -1;

    private LoginCallback mCallback;

    private WeakReference<Context> mContext;

    public User(Context context){
        mContext = new WeakReference<Context>(context);
    }

    public void login(String user,String pwd){
        //异步处理登录
        new LoginTask().execute(new String[]{user,pwd});
    }

    public LoginCallback getCallback() {
        return mCallback;
    }

    public void setCallback(LoginCallback mCallback) {
        this.mCallback = mCallback;
    }

    class LoginTask extends AsyncTask<String, Void, Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            int result = 0;

            if(!checkNetwork()){
                //无网络，登陆异常
                result = CODE_ERROR;
            }else{

                if(params[0].equals(USER)&&params[1].equals(PWD)){
                    //登录成功
                    result = CODE_OK;
                }else{
                    //登录失败
                    result = CODE_FAILED;
                }

                //模拟网络耗时操作
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            //处理登录结果
            switch(integer){
                case CODE_OK:
                    if(mCallback != null){
                        mCallback.onSuccessed();
                    }
                    break;

                case CODE_FAILED:
                    if(mCallback != null){
                        mCallback.onFailed();
                    }
                    break;

                case CODE_ERROR:
                    if(mCallback != null){
                        mCallback.onError();
                    }
                    break;

                default:
                    break;
            }
        }

        //检查网络是否连接成功
        private boolean checkNetwork() {
            // TODO Auto-generated method stub
            if(mContext.get() != null){
                ConnectivityManager cm = (ConnectivityManager) mContext.get().getSystemService(Context.CONNECTIVITY_SERVICE);
                if(cm.getActiveNetworkInfo() != null){
                    return cm.getActiveNetworkInfo().isConnected();
                }
            }
            return false;
        }

    }


}

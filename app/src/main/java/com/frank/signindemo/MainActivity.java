package com.frank.signindemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity  implements  LoginCallback{

    EditText mEtUser,mEtPwd;
    Button mBtnLogin;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initviews() {
        // TODO Auto-generated method stub
        mEtUser = (EditText) findViewById(R.id.et_user);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                User user = new User(MainActivity.this);
                user.setCallback(MainActivity.this);
                user.login(mEtUser.getEditableText().toString(),mEtPwd.getEditableText().toString());
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // 启动进度条对话框
                        mProgressDialog = ProgressDialog.show(MainActivity.this, "Loging", "please wait a mement...");
                    }
                });
            }
        });
    }


    //取消进度对话框
    private void dissMissDialog() {
        if(mProgressDialog != null){
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    mProgressDialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onSuccessed() {
        Toast.makeText(MainActivity.this, "Login successed!",Toast.LENGTH_SHORT).show();
        dissMissDialog();
    }


    @Override
    public void onFailed() {
        Toast.makeText(MainActivity.this, "Login failed,user or password is invalidated!",Toast.LENGTH_SHORT).show();
        dissMissDialog();

    }

    @Override
    public void onError() {
        Toast.makeText(MainActivity.this, "Login occus error!,please check whether the network is available!",Toast.LENGTH_SHORT).show();
        dissMissDialog();

    }
}

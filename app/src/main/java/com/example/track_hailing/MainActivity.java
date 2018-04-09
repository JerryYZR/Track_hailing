package com.example.track_hailing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    // 声明控件对象
    private EditText et_name;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.username);
        mLoginButton = (Button) findViewById(R.id.login);

        mLoginButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(MainActivity.this, LBSActivity.class));
    }
}

package com.example.track_hailing;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class VertificationActivity extends AppCompatActivity {

    private VerificationCodeInput verificationCodeInput;
    private Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vertification);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);

        verificationCodeInput = (VerificationCodeInput) findViewById(R.id.verificationCodeInput);

        verificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                btn_confirm.setEnabled(true);
                btn_confirm.setTextColor(Color.parseColor("#ff0000"));
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VertificationActivity.this, LBSActivity.class));
            }
        });
    }
}

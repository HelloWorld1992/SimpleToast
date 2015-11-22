package com.belief.simpletoast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.belief.simpletoast.util.ToastUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.toast_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtil.show("自定义时长", 5000);
                //ToastUtil.show("LengthLong", ToastUtil.LENGTH_LONG);
                ToastUtil.show("LengthShort", ToastUtil.LENGTH_SHORT);
            }
        });
    }
}

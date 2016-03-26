package com.example.aimee.bottombar;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Aimee on 2016/3/10.
 */
public class SignFragment extends Fragment {
    private EditText edit_phone;
    private EditText edit_password;
    private Button btn_submit;
    private Button btn_forrget;
    private String phone;
    private String password;
    private Handler handler;
    private static final int RETRY_INTERVAL =6;
    private int time=RETRY_INTERVAL;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View sign=inflater.inflate(R.layout.signin,container,false);
       init(sign);

        return sign;
    }

    private void init(View sign)
    {
        handler=new Handler();
        edit_phone = (EditText) sign.findViewById(R.id.username);
        edit_password = (EditText) sign.findViewById(R.id.password);
        btn_submit = (Button) sign.findViewById(R.id.button);
        btn_forrget = (Button) sign.findViewById(R.id.forget);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {//把获取验证码的按钮变成无法按下去，并且倒计时

                    @Override
                    public void run() {
                        while(time-- > 0) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    btn_submit.setText("已发送(" +time+"s)");
                                    btn_submit.setEnabled(false);
                                    btn_submit.setTextColor(getResources().getColor(R.color.white));
                                    btn_submit.setBackgroundColor(getResources().getColor(R.color.pressed));
                                }

                            });
                            try{
                                Thread.sleep(1000);
                            }catch (Exception E)
                            {
                                //NOTHING
                            }
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                time = RETRY_INTERVAL;
                                btn_submit.setText("获取验证码");
                                btn_submit.setEnabled(true);
                                btn_submit.setBackgroundColor(getResources().getColor(R.color.orange));
                            }
                        });


                    }
                }).start();
            }
        });
    }

}

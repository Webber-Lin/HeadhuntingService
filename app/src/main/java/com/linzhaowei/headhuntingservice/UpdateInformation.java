package com.linzhaowei.headhuntingservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UpdateInformation extends AppCompatActivity {
    private EditText realname;         //真实姓名
    private RadioGroup sex;               //性别
    private RadioButton what_sex;       //性别
    private EditText email;             //E_mail
    private EditText education;         //学历
    private EditText tel;                //电话
    private EditText native_place;      //籍贯

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);
    }


    private void initViews(){
        realname=findViewById(R.id.r_realname);
        sex=findViewById(R.id.r_sex);
        what_sex=findViewById(sex.getCheckedRadioButtonId());
        email=findViewById(R.id.r_email);
        education=findViewById(R.id.r_education);
        tel=findViewById(R.id.r_tel);
        native_place=findViewById(R.id.r_nativeplace);
    }

}

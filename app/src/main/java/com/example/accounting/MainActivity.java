package com.example.accounting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button login_btn;
    TextView goToSignUp;
    EditText phone_number;
    EditText password_txt;
    AccountingDataBase appDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password_txt=findViewById(R.id.password_txt);
        phone_number=findViewById(R.id.phone_number);
        login_btn=findViewById(R.id.login_btn);
        goToSignUp=findViewById(R.id.goToSignUp);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone=phone_number.getText().toString();
                String password=password_txt.getText().toString();
                if(phone.isEmpty() || password
                        .isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Fill All Data", Toast.LENGTH_SHORT).show();
                }else{
                   if(appDataBase==null)appDataBase=new AccountingDataBase(MainActivity.this);
                    boolean b = appDataBase.login(phone,password)==-1;
                    if(b){
                        Toast.makeText(MainActivity.this, "Error In Login", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(MainActivity.this,SignUpActivity.class));
             finish();
            }
        });
    }
}
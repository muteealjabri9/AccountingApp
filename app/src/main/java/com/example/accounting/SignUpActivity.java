package com.example.accounting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {


    Button sign_up_btn;
    TextView backToLogin;
    EditText first_name_txt;
    EditText last_name_txt;

    EditText phone_number;
    EditText password_txt;

    AccountingDataBase appDataBase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        password_txt=findViewById(R.id.password_txt);
        phone_number=findViewById(R.id.phone_number);
        last_name_txt=findViewById(R.id.last_name_txt);
        first_name_txt=findViewById(R.id.first_name_txt);
        sign_up_btn=findViewById(R.id.sign_up_btn);
        backToLogin=findViewById(R.id.backToLogin);

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName=first_name_txt.getText().toString().trim();
                String lastName=last_name_txt.getText().toString().trim();
                String phoneNumber=phone_number.getText().toString().trim();
                String password=password_txt.getText().toString().trim();

                if(firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() ){
                    Toast.makeText(SignUpActivity.this, "Please Fill All Required Data", Toast.LENGTH_SHORT).show();
                }else {
                    if(appDataBase==null)appDataBase=new AccountingDataBase(SignUpActivity.this);
                    boolean b = appDataBase.checkIfPhoneNumberRegistered(phoneNumber);
                    if(b){
                        Toast.makeText(SignUpActivity.this, "Phone Number Already Registered", Toast.LENGTH_SHORT).show();
                    }else {
                        long register = appDataBase.register(firstName, lastName, phoneNumber,  password);
                        if(register==-1){
                            Toast.makeText(SignUpActivity.this, "Error In Create Account", Toast.LENGTH_SHORT).show();

                        }else{
                            Intent intent=new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }

            }
        });
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}
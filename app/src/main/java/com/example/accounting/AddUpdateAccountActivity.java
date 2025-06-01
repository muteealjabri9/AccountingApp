package com.example.accounting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddUpdateAccountActivity extends AppCompatActivity {
    private EditText price;
    private EditText details;
    private RadioGroup group;
    private AccountData accountData;
    private UserSession userSession;
    AccountingDataBase accountingDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_account);
        price=findViewById(R.id.price);
        details=findViewById(R.id.details);
        Button save = findViewById(R.id.save_btn);
        group=findViewById(R.id.group);
        userSession=new UserSession(this);
        accountData= (AccountData) getIntent().getSerializableExtra("data");
        if(accountData!=null){
            price.setText(String.valueOf(Math.abs(accountData.getPrice())));
            details.setText(accountData.getDetails());
            if(accountData.getPrice()<0){
                group.check(R.id.debtor);
            }else {
                group.check(R.id.creditor);
            }
        }
        save.setOnClickListener(v -> {
            String priceTxt=price.getText().toString();
            String detailsTxt=details.getText().toString();
            if(priceTxt.isEmpty() || detailsTxt.isEmpty()){
                Toast.makeText(AddUpdateAccountActivity.this, "Please Fill Data", Toast.LENGTH_SHORT).show();
            }else{

                accountingDataBase=new AccountingDataBase(AddUpdateAccountActivity.this);
                if(accountData==null){
                    // add new
                    double price=group.getCheckedRadioButtonId()==R.id.creditor?Double.parseDouble(priceTxt):Double.parseDouble("-"+priceTxt);
                    accountData=new AccountData(userSession.getUserId(),detailsTxt,price);
                    accountingDataBase.insertAccount(accountData);

                }else{
                    // update
                    double price=group.getCheckedRadioButtonId()==R.id.creditor?Double.parseDouble(priceTxt):Double.parseDouble("-"+priceTxt);
                    accountData.setDetails(detailsTxt);
                    accountData.setPrice(price);
                    accountingDataBase.updateAccount(accountData);
                }
                Toast.makeText(AddUpdateAccountActivity.this,"Account Saved",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
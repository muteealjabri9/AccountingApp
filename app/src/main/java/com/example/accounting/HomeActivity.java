package com.example.accounting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView no_data;
    TextView totalTxt;

    private double total=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.list_item);
        no_data=findViewById(R.id.no_data);
        totalTxt=findViewById(R.id.total);
        findViewById(R.id.floating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(HomeActivity.this,AddUpdateAccountActivity.class);
                startActivity(intent);
            }
        });

    }

    AccountingDataBase appDataBase;
    @Override
    protected void onStart() {
        super.onStart();
        getAll();
    }

    private void getAll() {
        if(appDataBase==null)appDataBase=new AccountingDataBase(this);
        ArrayList<AccountData> list=appDataBase.getAllAccount();
        total=0.0;
        if(list.isEmpty()){
            recyclerView.setVisibility(View.GONE);
            no_data.setVisibility(View.VISIBLE);

        }else{
            for (AccountData accountData : list) {
                total=total+accountData.getPrice();
            }

            recyclerView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
            AccountListAdapter adapter=new AccountListAdapter(this);
            recyclerView.setAdapter(adapter);
            adapter.submitList(list);

        }
        totalTxt.setText(String.valueOf(total));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void delete(int id){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are You Sure to Delete");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                appDataBase.delete(id);
                getAll();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();

    }
}
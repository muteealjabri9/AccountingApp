package com.example.accounting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class AccountListAdapter extends ListAdapter<AccountData, AccountListAdapter.AccountViewHolder> {

    public static final DiffUtil.ItemCallback<AccountData> CALLBACK=new DiffUtil.ItemCallback<AccountData>() {
        @Override
        public boolean areItemsTheSame(@NonNull AccountData oldItem, @NonNull AccountData newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AccountData oldItem, @NonNull AccountData newItem) {
            return false;
        }
    };
    private final Context context;
    public AccountListAdapter(Context context){
        super(CALLBACK);
        this.context=context;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        AccountData accountData=getItem(position);
        holder.details.setText(accountData.getDetails());
        holder.price.setText(String.valueOf(accountData.getPrice()));
        holder.image.setImageResource(accountData.getPrice()>0?R.drawable.baseline_keyboard_arrow_up_24:R.drawable.baseline_keyboard_arrow_down_24);

        holder.itemView.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("Select Action");
            builder.setItems(new CharSequence[]{"Delete", "Edit"}, (dialog, which) -> {
                dialog.cancel();
                if(which==0){
                    ((HomeActivity)context).delete(accountData.getId());
                }
                if(which==1){
                    Intent intent =new Intent(context,AddUpdateAccountActivity.class);
                    intent.putExtra("data",accountData);
                    context.startActivity(intent);
                }
            }).create().show();
        });
    }

    public static class  AccountViewHolder extends RecyclerView.ViewHolder{
        public TextView price;
        public TextView details;
        public ImageView image;


        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.price);
            details=itemView.findViewById(R.id.details);
            image=itemView.findViewById(R.id.image);
        }
    }
}

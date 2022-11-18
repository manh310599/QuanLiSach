package com.example.quanlisach.Bill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlisach.R;

import java.util.List;


public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder>{


    private final List<Bill> bills;
    private final Context context;


    public BillAdapter(List<Bill> bills,Context context)
    {
        this.context = context;
        this.bills = bills;

    }




    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill,parent,false);
        return new BillViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, @SuppressLint("RecyclerView") int position){
        Bill bill = bills.get(position);


        if (bill == null){
            return;
        }

        Glide.with(context).load(bills.get(position).getImage()).into(holder.imageBook);
        holder.Namebook.setText("Tên sách: "+bill.getName_Book());
        holder.Seller.setText("Người bán: "+bill.getSeller());
        holder.Buyer.setText("Người mua: "+bill.getBuyer());
        holder.Gia.setText("Giá của hóa đơn"+String.valueOf(bill.getTotal_price() ));
    }

    @Override
    public int getItemCount() {
        if (bills != null)
        {
            return bills.size();
        }
        return 0;
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageBook;
        private final TextView Namebook;
        private final TextView Seller;
        private final TextView Buyer;
        private final TextView Gia;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);

            imageBook = itemView.findViewById(R.id.imageBook);
            Namebook = itemView.findViewById(R.id.NameBook);
            Seller = itemView.findViewById(R.id.Seller);
            Buyer = itemView.findViewById(R.id.Buyer);
            Gia = itemView.findViewById(R.id.Gia);

        }
    }
}
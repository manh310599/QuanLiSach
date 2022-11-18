package com.example.quanlisach.Book;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlisach.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class Search_book extends RecyclerView.Adapter<Search_book.BookViewHolder>{


    private final List<Book> bookList;
    private final Context context;


    public Search_book(List<Book> bookList,Context context)
    {
        this.context = context;
        this.bookList = bookList;

    }



    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Book book = bookList.get(position);

        if (book == null){
            return;
        }
        // holder.img.setImageResource(book.getResourceImage());
        holder.tv.setText(book.getAuthor());
        holder.NguoiBan.setText(book.getName_book());
        Glide.with(context).load(bookList.get(position).getImage()).into(holder.ImageBook);

        holder.information.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_book);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }
                else {
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    WindowManager.LayoutParams windowatributes = window.getAttributes();
                    windowatributes.gravity = Gravity.CENTER;
                    window.setAttributes(windowatributes);
                }

                TextView TenSach,TenTacGia,GiaSach,NgonNgu,NguoiBan,SoLuongCon,like;
                Button DatHang,Close;
                ImageView HinhSach;
                ImageButton heart;

                Close = dialog.findViewById(R.id.Close);
                Close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                TenSach = dialog.findViewById(R.id.NameBook);
                TenTacGia = dialog.findViewById(R.id.TacGia);
                GiaSach = dialog.findViewById(R.id.Gia);
                NgonNgu = dialog.findViewById(R.id.Language);
                NguoiBan = dialog.findViewById(R.id.Seller);
                SoLuongCon = dialog.findViewById(R.id.Con);
                HinhSach = dialog.findViewById(R.id.img_book);
                like = dialog.findViewById(R.id.like);
                heart = dialog.findViewById(R.id.heart);
                DatHang = dialog.findViewById(R.id.DatHang);
                EditText SoLuong  = dialog.findViewById(R.id.SoLuong);
                TextView TongTien = dialog.findViewById(R.id.TongTien);

                TenSach.setText("Tên sách: " + String.valueOf(book.getName_book()));
                TenTacGia.setText("Tên tác giả: " + String.valueOf(book.getAuthor()));
                GiaSach.setText("Giá sách: " + String.valueOf(book.getPrice()));
                NgonNgu.setText("Ngôn ngữ: "+String.valueOf(book.getLanguage()));
                NguoiBan.setText(String.valueOf("Người bán: "+book.getSeller()));
                SoLuongCon.setText(String.valueOf("Số lượng còn: "+book.getRemaining_stock()));
                Glide.with(context).load(bookList.get(position).getImage()).into(HinhSach);
                like.setText(String.valueOf(book.getLikes()));


                DatHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SoLuong.getText().toString().equals(""))
                            Toast.makeText(context, "Xin vui lòng nhập vào số lượng hàng mà bạn muốn đặt", Toast.LENGTH_SHORT).show();
                        else if (Integer.parseInt(SoLuong.getText().toString()) <= book.getRemaining_stock())
                        {
                            TongTien.setText("Tổng tiền: "+ book.getPrice()*Integer.parseInt(SoLuong.getText().toString()));
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference().child("Sach"+"/Book"+book.getID_Book()+"/remaining_stock");

                            myRef.setValue(book.getRemaining_stock() - Integer.parseInt(SoLuong.getText().toString()), new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                }
                            });
                            SoLuongCon.setText(String.valueOf("Số lượng còn: "+(book.getRemaining_stock() - Integer.parseInt(SoLuong.getText().toString()))));
                            Toast.makeText(context, "Cảm ơn bạn đã sử dụng dịch vụ bạn đã đặt hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(context, "Số lượng đặt hàng đã vượt quá số lượng hàng còn lại của mặt hàng", Toast.LENGTH_SHORT).show();
                    }
                });
                heart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        like.setText(String.valueOf((int) book.getLikes()+1));
                        heart.setBackgroundColor(R.color.red);
                    }
                });

                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (bookList != null)
        {
            return bookList.size();
        }
        return 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {


        //private ImageView img;
        private final TextView tv;
        private final TextView NguoiBan;
        private final ImageView ImageBook;
        private final LinearLayout information;


        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            ImageBook = itemView.findViewById(R.id.img_book);
            tv = itemView.findViewById(R.id.tv_name);
            NguoiBan = itemView.findViewById(R.id.tv_namePerson);
            information = itemView.findViewById(R.id.Book);

        }
    }
}
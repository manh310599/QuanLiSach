package com.example.quanlisach.Book;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanlisach.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class EditBookAdapter extends RecyclerView.Adapter<EditBookAdapter.BookViewHolder>{


    private final List<Book> bookList;
    private final Context context;
    private IClick iClick;

    public EditBookAdapter(List<Book> bookList,Context context,IClick iClick)
    {
        this.context = context;
        this.bookList = bookList;
        this.iClick = iClick;
    }

    public interface IClick {
        void onclickUpdate(Book book);
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_item_book,parent,false);
        return new BookViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        if (book == null){
            return;
        }
        // holder.img.setImageResource(book.getResourceImage());
        holder.tv.setText(book.getAuthor());
        holder.NguoiBan.setText(book.getName_book());
        Glide.with(context).load(bookList.get(position).getImage()).into(holder.ImageBook);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_edit_book);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                } else {
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    WindowManager.LayoutParams windowatributes = window.getAttributes();
                    windowatributes.gravity = Gravity.CENTER;
                    window.setAttributes(windowatributes);


                    Button close = dialog.findViewById(R.id.Close);
                    Button Save = dialog.findViewById(R.id.save);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    EditText GiaSach,TenSach,SoLuongCon;
                    ImageView ImgEdit;
                    GiaSach = dialog.findViewById(R.id.GiaSach);
                    TenSach = dialog.findViewById(R.id.TenSach);
                    SoLuongCon = dialog.findViewById(R.id.SoLuongCon);
                    ImgEdit = dialog.findViewById(R.id.ImgEdit);

                    GiaSach.setText(String.valueOf(book.getPrice()));
                    TenSach.setText(String.valueOf(book.getName_book()));
                    SoLuongCon.setText(String.valueOf(book.getRemaining_stock()));
                    Glide.with(context).load(bookList.get(position).getImage()).into(ImgEdit);
                    Save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference().child("Sach");

                            int a = Integer.parseInt(GiaSach.getText().toString());
                            int b = Integer.parseInt(SoLuongCon.getText().toString());
                            book.setName_book(TenSach.getText().toString().trim());
                            book.setPrice(a);
                            book.setRemaining_stock(b);

                            myRef.child(String.valueOf("Book"+book.getID_Book())).updateChildren(book.toMap(), new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(context, "Cập nhật dữ liệu thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    });



                    dialog.show();


                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // set Message là phương thức thiết lập câu thông báo
                builder.setMessage("Bạn có chắc muốn xóa cuốn sách này không")
                        // positiveButton là nút thuận : đặt là OK
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                                Query myRef =  database.child("Sach").orderByChild("id_Book").equalTo(book.getID_Book());

                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        })
                        // ngược lại negative là nút nghịch : đặt là cancel
                        .setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // tạo dialog và hiển thị
                builder.create().show();
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
        private final ImageView edit;
        private final ImageView delete;


        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            ImageBook = itemView.findViewById(R.id.img_book);
            tv = itemView.findViewById(R.id.tv_name);
            NguoiBan = itemView.findViewById(R.id.tv_namePerson);

            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
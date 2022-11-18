package com.example.quanlisach.Fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisach.Book.Book;
import com.example.quanlisach.Book.EditBookAdapter;
import com.example.quanlisach.R;
import com.example.quanlisach.Tam.Tam;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Setting_Fragment extends Fragment {
    private static final int PICK_IMAGE = 100;
    private List<Book> books;
    private RecyclerView recyclerView;
    private EditBookAdapter bookAdapter;
    private Button ThemSach;
    public String ImageUri;

    private final int PICK_IMAGE_REQUEST = 22;
    ImageView image;

    private Book book;


    // Uri indicates, where the image will be picked from


    // instance for firebase storage and StorageReference


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.setting_fragment, container, false);

        recyclerView = root.findViewById(R.id.rcv_book);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        DividerItemDecoration dividerItemDecoration  = new DividerItemDecoration(root.getContext(),DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        books = new ArrayList<>();
        bookAdapter = new EditBookAdapter(books, getContext(), new EditBookAdapter.IClick() {
            @Override
            public void onclickUpdate(Book book) {
                openDialogUpdateItem(book);
            }
        });

         ThemSach = root.findViewById(R.id.ThemSach);
        ThemSach.setOnClickListener(new View.OnClickListener() {
            long id = new Random().nextInt(900001) + 10000;
            @Override
            public void onClick(View view) {
                ThemSachMoi();
            }
        });

        getListBook();
        recyclerView.setAdapter(bookAdapter);
        return root;

    }
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @SuppressLint("ResourceAsColor")
    private void ThemSachMoi() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.them_sach_dialog);


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
        Button ChonHinh = dialog.findViewById(R.id.ChonHinh);
        image = dialog.findViewById(R.id.img_book1);
        Button Luu = dialog.findViewById(R.id.Luu);
        Button Close = dialog.findViewById(R.id.Close);
        EditText TenSach = dialog.findViewById(R.id.NameBook);
        EditText TenTacGia = dialog.findViewById(R.id.TenTacGia);
        EditText GiaSach = dialog.findViewById(R.id.GiaSach);
        EditText NgonNgu = dialog.findViewById(R.id.NgonNgu);
        EditText Soluong = dialog.findViewById(R.id.SoLuong);
        Luu.setEnabled(false);
        Luu.setBackgroundColor(R.color.black);

        ChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage();

                Luu.setEnabled(true);
                Luu.setBackgroundResource(R.drawable.custum_button1);

            }
        });
        Luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (TenSach.getText().toString().equals("") || TenTacGia.getText().toString().equals("") || NgonNgu.getText().toString().equals("") ||GiaSach.getText().toString().equals("") || Soluong.getText().toString().equals("") ){
                    Toast.makeText(getContext(), "Hãy nhập vào đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                }
                else {
                    Calendar calendar = Calendar.getInstance();

                    StorageReference mountainsRef = storageRef.child("image" +calendar.getTimeInMillis()+".png");
                    image.setDrawingCacheEnabled(true);
                    image.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] data = baos.toByteArray();


                UploadTask uploadTask = mountainsRef.putBytes(data);





                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getContext(), "Lỗi hình ảnh không được tải lên", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                        long id = new Random().nextInt(900001) + 10000;
                        String NameBook = TenSach.getText().toString();
                        String Author = TenTacGia.getText().toString();
                        String Image = "https://firebasestorage.googleapis.com/v0/b/doanjava-8a230.appspot.com/o/"+mountainsRef.getName()+"?alt=media&token=75ea54fb-9aff-434c-ae3e-c2a6c53e6946";
                        String Langue  = NgonNgu.getText().toString();
                        long Gia = Integer.parseInt(GiaSach.getText().toString());
                        long SoLuongSach = Integer.parseInt(Soluong.getText().toString());
                        long a = 0;
                        Book book = new Book(Author,NameBook,Image, id,Langue,a,Gia,Tam.UserName,SoLuongSach);


                       AddBook(book);
                       dialog.dismiss();
                    }

                    private void AddBook(Book book) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference().child("Sach");

                        String pathObject = String.valueOf("Book"+book.getID_Book());
                        myRef.child(pathObject).setValue(book);
                    }

                });
            }
            }



        });
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });




        dialog.show();
    }

    private void SelectImage() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    image.setImageURI(selectedImage);
                    addToStorage(selectedImage);

                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    image.setImageURI(selectedImage);
                    addToStorage(selectedImage);
                }
                break;
        }
    }

    private void addToStorage(Uri selectedImage) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    }


    private void openDialogUpdateItem(Book book) {
    }

    private void getListBook () {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Sach");
        myRef.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Book book  = snapshot.getValue(Book.class);
                if (book != null)
                {
                    if (book.getSeller().contains(Tam.UserName))
                    {
                        books.add(book);
                    }
                    bookAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Book book = snapshot.getValue(Book.class);
                if (book == null || books == null ||books.isEmpty())
                {
                    return;
                }
                for (int i = 0;i < books.size();i++)
                {
                    if (book.getID_Book() == books.get(i).getID_Book())
                    {
                        books.remove(books.get(i));
                        break;
                    }
                }
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

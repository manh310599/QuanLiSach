package com.example.quanlisach.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisach.Book.Book;
import com.example.quanlisach.Book.BookAdapter;
import com.example.quanlisach.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class search_fragment extends Fragment {
    private List<Book> books;

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private Button Cheaped,all,Featured_book;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_fragment, container, false);

        recyclerView = root.findViewById(R.id.rcv_book);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        DividerItemDecoration dividerItemDecoration  = new DividerItemDecoration(root.getContext(),DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        books = new ArrayList<>();
        bookAdapter = new BookAdapter(books,getContext(),null);

        EditText SeachBook = root.findViewById(R.id.seachBook);

        ImageButton Go = root.findViewById(R.id.Go);

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                books.clear();
                GetTheBook(SeachBook.getText().toString());
            }
        });

        getListBook();


        recyclerView.setAdapter(bookAdapter);
        return root;

    }

    private void GetTheBook(String a) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Sach");

         myRef.addChildEventListener(new ChildEventListener() {
             @SuppressLint("NotifyDataSetChanged")
             @Override
             public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                 Book book  = snapshot.getValue(Book.class);
                 if (book != null)
                 {
                     if (book.getName_book().contains(a))
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


    private void getListBook () {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Sach");
        myRef.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);


                if (book != null) {

                    books.add(book);
                    bookAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Book book = snapshot.getValue(Book.class);
                if (book==null || books == null || books.isEmpty())
                {
                    return;
                }
                for (int i = 0;i < books.size();i++)
                {
                    if( book.getID_Book() == books.get(i).getID_Book() ){
                        books.set(i,book);
                    }
                }
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

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

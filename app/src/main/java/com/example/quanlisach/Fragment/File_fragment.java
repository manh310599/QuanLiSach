package com.example.quanlisach.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlisach.Bill.Bill;
import com.example.quanlisach.Bill.BillAdapter;
import com.example.quanlisach.R;
import com.example.quanlisach.Tam.Tam;
import com.example.quanlisach.User.User;
import com.example.quanlisach.User.UserAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class File_fragment extends Fragment {

    private List<User> users;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;

    private List<Bill> bills;
    private BillAdapter billAdapter;
    private RecyclerView recyclerView1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.file_fragment,container,false);

        recyclerView = root.findViewById(R.id.rcv_User);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        DividerItemDecoration dividerItemDecoration  = new DividerItemDecoration(root.getContext(),DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        users = new ArrayList<>();
        userAdapter = new UserAdapter(users,getContext());

        bills = new ArrayList<>();
        billAdapter = new BillAdapter(bills,getContext());

        recyclerView1 = root.findViewById(R.id.rcv_Bill);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(root.getContext());
        recyclerView1.setLayoutManager(linearLayoutManager1);

        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(),1));

        DividerItemDecoration dividerItemDecoration1  = new DividerItemDecoration(root.getContext(),DividerItemDecoration.HORIZONTAL);
        recyclerView1.addItemDecoration(dividerItemDecoration1);



        getUser();
        getBill();


        recyclerView1.setAdapter(billAdapter);
        recyclerView.setAdapter(userAdapter);
        return root;
    }

    private void getBill() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("bill");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Bill bill = snapshot.getValue(Bill.class);


                if (bill != null) {
                    if (bill.getBuyer().contains(Tam.UserName) || bill.getSeller().contains(Tam.UserName)){
                        bills.add(bill);
                    }

                    billAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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


    private void getUser() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Account");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);


                if (user != null) {
                    if (user.getAccount().contains(Tam.UserName)) {
                        users.add(user);
                        userAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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

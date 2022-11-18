package com.example.quanlisach;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlisach.Fragment.File_fragment;
import com.example.quanlisach.Fragment.Home_Flagment;
import com.example.quanlisach.Fragment.Setting_Fragment;
import com.example.quanlisach.Fragment.search_fragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Home_Flagment();
            case 1:
                return new search_fragment();

            case 2:
                return new Setting_Fragment();
            case 3:
                return new File_fragment();
            default:
                return new Home_Flagment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

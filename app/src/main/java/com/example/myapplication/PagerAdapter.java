package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {

    private int tabnum;
    private String ticker;

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int tabnum,String ticker) {
        super(fragmentManager, lifecycle);
        this.tabnum = tabnum;
        this.ticker = ticker;
    }


//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//

//    }

//    @Override
//    public int getCount() {
//        return tabnum;
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new tab1(ticker);
            case 1:
                return new tab2(ticker);
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return tabnum;
    }
}

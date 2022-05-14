package com.example.clothme.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.clothme.Fragments.BottomWearFragment;
import com.example.clothme.Fragments.OuterWearFragment;
import com.example.clothme.Fragments.TopWearFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new TopWearFragment();
            case 1: return new BottomWearFragment();
            case 2: return  new OuterWearFragment();
            default: return  new TopWearFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0){
            title="TOP WEAR";
        }
        else if(position==1){
            title="BOTTOM WEAR";
        }else{
            title="OUTER WEAR";
        }
        return title;
    }
}

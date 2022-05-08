package com.example.clothme.ui.DisplayWardrobe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.clothme.Adapter.FragmentAdapter;
import com.example.clothme.R;
import com.example.clothme.databinding.FragmentSlideshowBinding;
import com.google.android.material.tabs.TabLayout;

public class SlideshowFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager2;

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tabLayout=root.findViewById(R.id.tablayout);
        viewPager2=root.findViewById(R.id.viewPager_display_outfits);
//        FragmentManager fm=getParentFragmentManager();
//        FragmentAdapter fa=new FragmentAdapter(fm);
        viewPager2.setAdapter(new FragmentAdapter(getChildFragmentManager() ));
        tabLayout.setupWithViewPager(viewPager2);
//        tableLayout.s
//         tableLayout=root.findViewById();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
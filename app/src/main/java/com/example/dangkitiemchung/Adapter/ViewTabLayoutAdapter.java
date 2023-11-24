package com.example.dangkitiemchung.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dangkitiemchung.Fragment.DaHuyFragment;
import com.example.dangkitiemchung.Fragment.DaLenLichFragment;


public class ViewTabLayoutAdapter extends FragmentStateAdapter {

    public ViewTabLayoutAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public ViewTabLayoutAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ViewTabLayoutAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new DaLenLichFragment();
            case 1:
                return new DaHuyFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

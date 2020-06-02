package com.example.todoapp.ui.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class FragmentPageAdapter extends FragmentStatePagerAdapter {

    private Context context; // context for current fragment adapter for current activity
    private List<Fragment> fragments = new ArrayList<>(); // can't use hashmap because of getItem() method
    private List<String> fragmentTitles = new ArrayList<>();

    public FragmentPageAdapter(FragmentManager fm, final Context context) {
        super(fm);
        this.context = context;
    }

    public void addFragments(Fragment fragment, String key) {
        fragments.add(fragment);
        fragmentTitles.add(key);
    }

    @Override
    public Fragment getItem(int position) {

        String tag = fragmentTitles.get(position);

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public Context getContext() {
        return context;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public List<String> getFragmentTitles() {
        return fragmentTitles;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public void setFragmentTitles(List<String> fragmentTitles) {
        this.fragmentTitles = fragmentTitles;
    }
}

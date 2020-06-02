package com.example.todoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.todoapp.ui.adapter.FragmentPageAdapter;

public abstract class Activity extends AppCompatActivity {
    protected FragmentPageAdapter fragmentPageAdapter; // init in child classes...
    protected ViewPager viewPager;

    public Activity(FragmentPageAdapter fragmentPageAdapter, ViewPager viewPager) {
        this.fragmentPageAdapter = fragmentPageAdapter;
        this.viewPager = viewPager;
    }

    public FragmentPageAdapter getFragmentPageAdapter() {
        return fragmentPageAdapter;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setFragmentPageAdapter(FragmentPageAdapter fragmentPageAdapter) {
        this.fragmentPageAdapter = fragmentPageAdapter;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}

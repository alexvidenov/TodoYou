package com.example.todoapp.activities;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.todoapp.R;
import com.example.todoapp.ui.adapter.FragmentPageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public abstract class Activity extends AppCompatActivity {
    protected BottomNavigationView bottomNavigationView;
    protected FragmentPageAdapter fragmentPageAdapter; // init in child classes...
    protected ViewPager viewPager;
    protected Resources resources;

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
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

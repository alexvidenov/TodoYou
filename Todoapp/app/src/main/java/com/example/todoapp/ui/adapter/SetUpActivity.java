package com.example.todoapp.ui.adapter;

import androidx.viewpager.widget.ViewPager;

public interface SetUpActivity { // TODO: Use in MainActivity and other activities to setup viewpager and fragments
    void setUpViewPager(ViewPager viewPager);

    void initFragmentPageAdapter();
}

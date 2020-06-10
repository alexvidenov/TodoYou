package com.example.todoapp.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.todoapp.R;
import com.example.todoapp.ui.adapter.FragmentPageAdapter;
import com.example.todoapp.ui.adapter.SetUpActivity;
import com.example.todoapp.ui.ui_core.CreateTagFragment;
import com.example.todoapp.ui.ui_core.CreateToDoFragment;
import com.example.todoapp.ui.ui_core.EditToDoFragment;
import com.example.todoapp.ui.ui_core.ViewToDoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener,
                                                      ViewPager.OnPageChangeListener, SetUpActivity {
    private MenuItem currentMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // calls Activity.onCreate, initializing the viewpager and navigation
        setContentView(R.layout.activity_main);

        resources = getResources();

        setUpActivity(); // call all three implementations of inits
    }

    @Override
    public void initBottomNavigation() { // inits bottom nav
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    // bottom_navigation_listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_create_todo:
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_view_todo:
                viewPager.setCurrentItem(1);
                break;
            case R.id.action_create_tag:
                viewPager.setCurrentItem(2);
                break;
        }
        return false;
    }

    // viewPager_change_listener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {
        if(currentMenuItem != null) { // if there is a current menu item chosen
            currentMenuItem.setChecked(false); // deselect it
        } else {
            bottomNavigationView.getMenu().getItem(1).setChecked(false); // else go to the default one
        }

        currentMenuItem = bottomNavigationView.getMenu().getItem(position); // get the new menu item and reinit it

        currentMenuItem.setChecked(true); // set it being checked to true
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    @Override
    public void setUpViewPager() { // sets up view pager to show views from the given FragmentAdapter
        viewPager = findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(fragmentPageAdapter);
    }

    @Override
    public void initFragmentPageAdapter() {
        fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), this);

        fragmentPageAdapter.addFragments(new CreateToDoFragment(), "createToDoFragment");
        fragmentPageAdapter.addFragments(new ViewToDoFragment(), "viewToDoFragment");
        fragmentPageAdapter.addFragments(new CreateTagFragment(), "createTagFragment");
    }
}

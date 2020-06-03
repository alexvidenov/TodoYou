package com.example.todoapp.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.todoapp.R;
import com.example.todoapp.ui.adapter.FragmentPageAdapter;
import com.example.todoapp.ui.adapter.SetUpActivity;
import com.example.todoapp.ui.ui_core.CreateToDoFragment;
import com.example.todoapp.ui.ui_core.EditToDoFragment;
import com.example.todoapp.ui.ui_core.ViewToDoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends Activity implements BottomNavigationView.OnNavigationItemSelectedListener,
                                                      ViewPager.OnPageChangeListener, SetUpActivity {
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // calls Activity.onCreate, initializing the viewpager and navigation
        setContentView(R.layout.activity_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        initFragmentPageAdapter();
        setUpViewPager(viewPager);
    }

    // bottom_navigation_listener
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_create_todo:
                viewPager.setCurrentItem(0);
                break;
            case R.id.action_edit_todo:
                viewPager.setCurrentItem(1);
                break;
            case R.id.action_view_todo:
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
        if(menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }

        MenuItem currentItem = bottomNavigationView.getMenu().getItem(position);
        currentItem.setChecked(true);
        menuItem = currentItem;
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    @Override
    public void setUpViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(fragmentPageAdapter);
    }

    @Override
    public void initFragmentPageAdapter() {
        fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), this);

        fragmentPageAdapter.addFragments(new CreateToDoFragment(), "createToDoFragment");
        fragmentPageAdapter.addFragments(new EditToDoFragment(), "editToDoFragment");
        fragmentPageAdapter.addFragments(new ViewToDoFragment(), "viewToDoFragment");
    }
}

package com.example.alber.prova_tab_menu;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

public class MainActivityMenu extends AppCompatActivity {

    private static final String TAG="MainActivityMenu";
    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    INTERNET internet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mSectionsPageAdapter= new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager= (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }


    private void setUpViewPager(ViewPager viewPager){

        SectionsPageAdapter adapter= new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1_alumne_fragment(), "ALUMNES");
        adapter.addFragment(new Tab2_menu_fragment(), "MENU");
        adapter.addFragment(new Tab3_cuina_fragment(), "CUINA");
        viewPager.setAdapter(adapter);
    }





}

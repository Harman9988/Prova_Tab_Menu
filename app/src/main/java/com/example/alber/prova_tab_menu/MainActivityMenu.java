package com.example.alber.prova_tab_menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivityMenu extends AppCompatActivity {

    private static final String TAG="MainActivityMenu";
    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    public static final String PREFS_NAME = "LoginPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mSectionsPageAdapter= new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager= (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.commonmenus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu){
            Intent i = new Intent( getApplicationContext(),ActivityPDF.class );
            startActivity(i);
        }
        else if(id==R.id.logout){
            logout();
        }

        return super.onOptionsItemSelected( item );
    }

    private void setUpViewPager(ViewPager viewPager){

        SectionsPageAdapter adapter= new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1_alumne_fragment(), "ALUMNES");
        adapter.addFragment(new Tab2_cuina_fragment(), "CUINA");
        viewPager.setAdapter(adapter);
    }

    public void logout(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("logged");
        editor.commit();
        finish();
    }





}

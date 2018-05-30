package com.example.alber.prova_tab_menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.alber.prova_tab_menu.Alumne.Tab1_alumne_fragment;
import com.example.alber.prova_tab_menu.Cuina.Tab2_cuina_fragment;
import com.example.alber.prova_tab_menu.Main.ActivityLogin;
import com.example.alber.prova_tab_menu.Menu.ActivityMenu;

public class MainActivityMenu extends AppCompatActivity {

    private static final String TAG="MainActivityMenu";
    private SectionsPageAdapter mSectionsPageAdapter;
MÑ´
    <zxcv bnm,1º<
    private ViewPager mViewPager;
    //LoginPrefs
    public static final String PREFS_NAME = "sessio_guardada";


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
            Intent i = new Intent( getApplicationContext(),ActivityMenu.class );
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
        SharedPreferences preferences =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        finish();


        /*SharedPreferences mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);
        SharedPreferences.Editor editor=mPreferences.edit();
        editor.remove("UserName");
        editor.remove("PassWord");
        editor.commit();
        finish();*/
    }





}

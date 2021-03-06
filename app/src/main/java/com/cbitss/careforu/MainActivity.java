package com.cbitss.careforu;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , FragmentReminder.OnFragmentInteractionListener
        ,HealthTips.OnFragmentInteractionListener,Home.OnFragmentInteractionListener
         {

    public ImageView iv;
    TextView tv;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        setTaskDescription( new ActivityManager.TaskDescription("Health in Pocket",bitmap));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Health Monitor");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        Intent intent = getIntent();
        if(intent.getExtras()!=null)
        {
            if(intent.getExtras().getInt("id")!=0)
            {
                int i = intent.getExtras().getInt("id");
                String s=intent.getExtras().getString("text");
                View headerView =navigationView.inflateHeaderView(R.layout.nav_header_main);

                iv=(ImageView)headerView.findViewById(R.id.nav_imageView) ;
                tv=(TextView) headerView.findViewById(R.id.nav_tv);
                tv.setText("Hi, "+s);
                iv.setImageResource(i);

                SharedPreferences sp=getSharedPreferences("Shpr", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed=sp.edit();
                ed.putBoolean("logged",true);
                ed.putString("User",s);
                ed.putInt("imgRes",i);
                ed.commit();
                Home hm = new Home();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, hm).commit();
            }
            else {
                FragmentReminder rm = new FragmentReminder();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, rm).commit();
                SharedPreferences sp=getSharedPreferences("Shpr", Context.MODE_PRIVATE);
                int i = sp.getInt("imgRes",0);
                String s=sp.getString("User",null);
                View headerView =navigationView.inflateHeaderView(R.layout.nav_header_main);

                iv=(ImageView)headerView.findViewById(R.id.nav_imageView) ;
                tv=(TextView) headerView.findViewById(R.id.nav_tv);
                tv.setText("Hi, "+s);
                iv.setImageResource(i);
            }

        }
        else {
            Home hm = new Home();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, hm).commit();

            SharedPreferences sp=getSharedPreferences("Shpr", Context.MODE_PRIVATE);
            Boolean loggedin=sp.getBoolean("logged",false);


        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Log.d("Item:",item.toString());
        int id = item.getItemId();

        if (id == R.id.nav_medrem) {
            FragmentReminder rm=new FragmentReminder();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,rm).commit();

            // Handle the camera action
        }
        else if (id == R.id.nav_step) {
           Intent intent=new Intent(this,stepcounter.class);
           startActivity(intent);

        }




        else if (id == R.id.nav_healthtips) {
            HealthTips ht=new HealthTips();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,ht).commit();

        }


        else if (id == R.id.nav_abtus) {
            Intent intent=new Intent(this, AboutUs.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void share(MenuItem item) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
        String shareMessage= "\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "choose one"));

    }


         }

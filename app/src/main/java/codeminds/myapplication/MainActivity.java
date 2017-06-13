package codeminds.myapplication;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    public CardView mtabCard;

    private ViewPager vp_slider;
    private LinearLayout ll_dots;

    private NavigationView navigationView;
    SliderPagerAdapter sliderPagerAdapter;
    private TextView[] dots;
    int page_position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mtabCard = (CardView)findViewById(R.id.tab_card);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




       final TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        final TabHost.TabSpec spec1 = host.newTabSpec("Tab One");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.drawable.select_video, null));
        host.addTab(spec1);

        //Tab 2
        final TabHost.TabSpec spec2 = host.newTabSpec("Tab Two");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("", ResourcesCompat.getDrawable(getResources(), R.drawable.image, null));
        host.addTab(spec2);

        //Tab 3
        final TabHost.TabSpec spec3 = host.newTabSpec("Tab Three");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("",ResourcesCompat.getDrawable(getResources(), R.drawable.milestone, null));
        host.addTab(spec3);



        for (int i = 0; i < host.getTabWidget().getChildCount(); i++){
            host.getTabWidget().getChildAt(i).setPadding(10,25,10,25);
        }

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int id =host.getCurrentTab();

                ImageView image0= (ImageView) host.getTabWidget().getChildTabViewAt(0).findViewById(android.R.id.icon);
                ImageView image1= (ImageView) host.getTabWidget().getChildTabViewAt(1).findViewById(android.R.id.icon);
                ImageView image2= (ImageView) host.getTabWidget().getChildTabViewAt(2).findViewById(android.R.id.icon);


                if (id==0)
                {

                    image1.setImageResource(R.drawable.image);
                    image0.setImageResource(R.drawable.select_video);
                    image2.setImageResource(R.drawable.milestone);


                }
                else if(id==1)
                {

                    image1.setImageResource(R.drawable.select_image);
                    image0.setImageResource(R.drawable.video);
                    image2.setImageResource(R.drawable.milestone);
//
                }
                else if(id==2)
                {

                    image1.setImageResource(R.drawable.image);
                    image0.setImageResource(R.drawable.video);
                    image2.setImageResource(R.drawable.select_milestone);
//
                }
            }
        });



        init();


        addBottomDots(0);
    }




    private void init() {

        vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);

        sliderPagerAdapter = new SliderPagerAdapter(MainActivity.this);
        vp_slider.setAdapter(sliderPagerAdapter);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[4];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#FFFFFF"));
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

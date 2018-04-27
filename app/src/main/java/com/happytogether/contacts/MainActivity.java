package com.happytogether.contacts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.happytogether.contacts.processor.NonBlockingSingleThreadingProcessor;
import com.happytogether.contacts.processor.SingleThreadProcessor;
import com.happytogether.contacts.resource_manager.ResourceManagerTest;
import com.happytogether.contacts.task.QueryCallDurationByTimeTask;
import com.happytogether.contacts.task.QueryCallRecordByNameTask;
import com.happytogether.contacts.task.QueryCallRecordByNumTask;
import com.happytogether.contacts.task.QueryCallRecordByTimeTask;
import com.happytogether.framework.log.IDELogger;
import com.happytogether.framework.log.LogBus;
import com.happytogether.framework.processor.Processor;
import com.happytogether.framework.resouce_manager.ResourceManager;

import java.time.chrono.MinguoChronology;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ArrayAdapter<String> adapter;
    public List<String> contactsList = new ArrayList<>();

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new FrameworkInitialization();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber= "";
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + phoneNumber));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //运行时权限
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission_group.CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG
            },1);
        }
        //QueryCallDurationByTimeTask.test1();
        //QueryCallRecordByTimeTask.test1();
        //QueryCallRecordByNameTask.test1();
        //QueryCallRecordByNumTask.test1();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }


        return super.onOptionsItemSelected(item);
    }


    //delect.....

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//           return PlaceholderFragment.newInstance(position + 1);
            //tab切换
            switch(position) {
                case 0:
                    HistoryFragment tab1 = new HistoryFragment();
                    return tab1;
                case 1:
                    ContactsFragment tab2 = new ContactsFragment();
                    return tab2;
                case 2:
                    OtherFragment tab3 = new OtherFragment();
                    return tab3;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}

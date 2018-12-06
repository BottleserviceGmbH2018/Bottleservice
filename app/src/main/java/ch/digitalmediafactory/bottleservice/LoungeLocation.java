package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Chris Gacu on 19/02/2018.
 */

public class LoungeLocation extends AppCompatActivity{
    private static final String TAG = "LoungeLocation";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_location);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);


        Log.d(TAG, "onCreate: Starting.");
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerlocation);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabslocation);
        tabLayout.setupWithViewPager(mViewPager);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_dash:
                        Intent Intent0 = new Intent(LoungeLocation.this, LoungeDashboard.class);
                        startActivity(Intent0);
                        break;

                    case R.id.ic_inbox:
                        Intent Intent2 = new Intent(LoungeLocation.this, LoungeMessages.class);
                        startActivity(Intent2);
                        LoungeLocation.this.finish();
                        break;

                    case R.id.ic_loc:
                        break;

                    case R.id.ic_notif:
                        Intent Intent3 = new Intent(LoungeLocation.this, LoungeReserve.class);
                        startActivity(Intent3);
                        LoungeLocation.this.finish();
                        break;

                    case R.id.ic_acc:
                        Intent Intent4 = new Intent(LoungeLocation.this, LoungeAccount.class);
                        startActivity(Intent4);
                        LoungeLocation.this.finish();
                        break;

                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent = new Intent(LoungeLocation.this, AddNewLocation.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new LocationBarFragment(), "BAR");
        adapter.addFragment(new LocationLoungeFragment(), "LOUNGE");
        adapter.addFragment(new LocationEventFragment(), "EVENT");
        viewPager.setAdapter(adapter);
    }

    public void buttonViewAddNewLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), AddNewLocation.class);
        startActivity(Intent);
        this.finish();
    }


    public void buttonViewEditLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditLocation.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonViewBarLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LocationBar.class);
        startActivity(Intent);
        this.finish();
    }


    public void buttonLoungeViewLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LocationLounge.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonEventViewLocation(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LocationEvent.class);
        startActivity(Intent);
        this.finish();
    }


}

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
import android.view.MenuItem;

/**
 * Created by Chris Gacu on 19/02/2018.
 */

public class LoungeReserve extends AppCompatActivity{
    private static final String TAG = "LoungeReservation";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_reserve);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        Log.d(TAG, "onCreate: Starting.");
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerreservation);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsreservation);
        tabLayout.setupWithViewPager(mViewPager);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_dash:
                        Intent Intent0 = new Intent(LoungeReserve.this, LoungeDashboard.class);
                        startActivity(Intent0);
                        break;

                    case R.id.ic_inbox:
                        Intent Intent3 = new Intent(LoungeReserve.this, LoungeMessages.class);
                        startActivity(Intent3);
                        LoungeReserve.this.finish();
                        break;

                    case R.id.ic_loc:
                        Intent Intent2 = new Intent(LoungeReserve.this, LoungeLocation.class);
                        startActivity(Intent2);
                        LoungeReserve.this.finish();
                        break;

                    case R.id.ic_notif:
                        break;

                    case R.id.ic_acc:
                        Intent Intent5 = new Intent(LoungeReserve.this, LoungeAccount.class);
                        startActivity(Intent5);
                        LoungeReserve.this.finish();
                        break;

                }
                return false;
            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ReservationBarFragment(), "BAR");
        adapter.addFragment(new ReservationLoungeFragment(), "LOUNGE");
        adapter.addFragment(new ReservationEventFragment(), "EVENT");
        viewPager.setAdapter(adapter);
    }
}

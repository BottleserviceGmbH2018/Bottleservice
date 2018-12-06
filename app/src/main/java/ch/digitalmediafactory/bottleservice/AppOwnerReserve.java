package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AppOwnerReserve extends AppCompatActivity{
    private static final String TAG = "AppOwnerReserve";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_owner_reserve);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.appbottomNavView_Bar);
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
                        Intent Intent0 = new Intent(AppOwnerReserve.this, AppOwnerDashboard.class);
                        startActivity(Intent0);
                        AppOwnerReserve.this.finish();
                        break;

                    case R.id.ic_inbox:
                        Intent Intent3 = new Intent(AppOwnerReserve.this, AppOwnerMessages.class);
                        startActivity(Intent3);
                        AppOwnerReserve.this.finish();
                        break;

                    case R.id.ic_loc:
                        Intent Intent2 = new Intent(AppOwnerReserve.this, AppOwnerLocation.class);
                        startActivity(Intent2);
                        AppOwnerReserve.this.finish();
                        break;

                    case R.id.ic_notif:
                        break;

                    case R.id.ic_acc:
                        Intent Intent5 = new Intent(AppOwnerReserve.this, AppOwnerAccount.class);
                        startActivity(Intent5);
                        AppOwnerReserve.this.finish();
                        break;

                }
                return false;
            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppReservationBarFragment(), "BAR");
        adapter.addFragment(new AppReservationLoungeFragment(), "LOUNGE");
        adapter.addFragment(new AppReservationEventFragment(), "EVENT");
        viewPager.setAdapter(adapter);
    }
}

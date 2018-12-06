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

public class AppOwnerLocation extends AppCompatActivity {
    private static final String TAG = "AppOwnerLocation";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_owner_location);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.appbottomNavView_Bar);
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
                        Intent Intent0 = new Intent(AppOwnerLocation.this, AppOwnerDashboard.class);
                        startActivity(Intent0);
                        AppOwnerLocation.this.finish();
                        break;

                    case R.id.ic_inbox:
                        Intent Intent2 = new Intent(AppOwnerLocation.this, AppOwnerMessages.class);
                        startActivity(Intent2);
                        AppOwnerLocation.this.finish();
                        break;

                    case R.id.ic_loc:
                        break;

                    case R.id.ic_notif:
                        Intent Intent3 = new Intent(AppOwnerLocation.this, AppOwnerReserve.class);
                        startActivity(Intent3);
                        AppOwnerLocation.this.finish();
                        break;

                    case R.id.ic_acc:
                        Intent Intent4 = new Intent(AppOwnerLocation.this, AppOwnerAccount.class);
                        startActivity(Intent4);
                        AppOwnerLocation.this.finish();
                        break;

                }
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new AppBarFragment(), "BAR");
        adapter.addFragment(new AppLoungeFragment(), "LOUNGE");
        adapter.addFragment(new AppEventFragment(), "EVENT");
        viewPager.setAdapter(adapter);
    }
}

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

public class AppOwnerMessages extends AppCompatActivity{
    private static final String TAG = "AppOwnerMessages";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_owner_messages);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.appbottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);


        Log.d(TAG, "onCreate: Starting.");
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_dash:
                        Intent Intent0 = new Intent(AppOwnerMessages.this, AppOwnerDashboard.class);
                        startActivity(Intent0);
                        AppOwnerMessages.this.finish();
                        break;

                    case R.id.ic_inbox:
                        break;

                    case R.id.ic_loc:
                        Intent Intent2 = new Intent(AppOwnerMessages.this, AppOwnerLocation.class);
                        startActivity(Intent2);
                        AppOwnerMessages.this.finish();
                        break;

                    case R.id.ic_notif:
                        Intent Intent3 = new Intent(AppOwnerMessages.this, AppOwnerReserve.class);
                        startActivity(Intent3);
                        AppOwnerMessages.this.finish();
                        break;

                    case R.id.ic_acc:
                        Intent Intent4 = new Intent(AppOwnerMessages.this, AppOwnerAccount.class);
                        startActivity(Intent4);
                        AppOwnerMessages.this.finish();
                        break;

                }
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new InboxApp1(), "INBOX");
        adapter.addFragment(new OutboxApp2(), "OUTBOX");
        viewPager.setAdapter(adapter);
    }
}

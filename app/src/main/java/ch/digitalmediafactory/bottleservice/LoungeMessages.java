package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Chris Gacu on 19/02/2018.
 */

public class LoungeMessages extends AppCompatActivity{
    private static final String TAG = "LoungeMessages";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_messages);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
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
                        Intent Intent0 = new Intent(LoungeMessages.this, LoungeDashboard.class);
                    startActivity(Intent0);
                        break;

                    case R.id.ic_inbox:
                        break;

                    case R.id.ic_loc:
                        Intent Intent2 = new Intent(LoungeMessages.this, LoungeLocation.class);
                        startActivity(Intent2);
                        LoungeMessages.this.finish();
                        break;

                    case R.id.ic_notif:
                        Intent Intent3 = new Intent(LoungeMessages.this, LoungeReserve.class);
                        startActivity(Intent3);
                        LoungeMessages.this.finish();
                        break;

                    case R.id.ic_acc:
                        Intent Intent4 = new Intent(LoungeMessages.this, LoungeAccount.class);
                        startActivity(Intent4);
                        LoungeMessages.this.finish();
                        break;

                }
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new InboxFragment1(), "INBOX");
        adapter.addFragment(new OutboxFragment2(), "OUTBOX");
        viewPager.setAdapter(adapter);
    }
}

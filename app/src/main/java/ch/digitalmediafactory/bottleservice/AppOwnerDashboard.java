package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AppOwnerDashboard extends AppCompatActivity {
    private static final String TAG = "AppOwnerDashboard";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_owner_dashboard);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.appbottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_dash:
                        break;

                    case R.id.ic_inbox:
                        Intent Intent1 = new Intent(AppOwnerDashboard.this, AppOwnerMessages.class);
                        startActivity(Intent1);
                        AppOwnerDashboard.this.finish();
                        break;

                    case R.id.ic_loc:
                        Intent Intent2 = new Intent(AppOwnerDashboard.this, AppOwnerLocation.class);
                        startActivity(Intent2);
                        AppOwnerDashboard.this.finish();
                        break;

                    case R.id.ic_notif:
                        Intent Intent3 = new Intent(AppOwnerDashboard.this, AppOwnerReserve.class);
                        startActivity(Intent3);
                        AppOwnerDashboard.this.finish();
                        break;

                    case R.id.ic_acc:
                        Intent Intent4 = new Intent(AppOwnerDashboard.this, AppOwnerAccount.class);
                        startActivity(Intent4);
                        AppOwnerDashboard.this.finish();
                        break;

                }
                return false;
            }
        });
    }

    public void buttonOwnerApprovals(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), OwnerApprovals.class);
        startActivity(Intent);
    }

}

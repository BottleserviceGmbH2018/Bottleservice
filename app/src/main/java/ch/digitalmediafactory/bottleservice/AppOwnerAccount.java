package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AppOwnerAccount extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_owner_account);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.appbottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_dash:
                        Intent Intent0 = new Intent(AppOwnerAccount.this, AppOwnerDashboard.class);
                        startActivity(Intent0);
                        AppOwnerAccount.this.finish();
                        break;

                    case R.id.ic_inbox:
                        Intent Intent1 = new Intent(AppOwnerAccount.this, AppOwnerMessages.class);
                        startActivity(Intent1);
                        AppOwnerAccount.this.finish();
                        break;

                    case R.id.ic_loc:
                        Intent Intent2 = new Intent(AppOwnerAccount.this, AppOwnerLocation.class);
                        startActivity(Intent2);
                        AppOwnerAccount.this.finish();
                        break;

                    case R.id.ic_notif:
                        Intent Intent3 = new Intent(AppOwnerAccount.this, AppOwnerReserve.class);
                        startActivity(Intent3);
                        AppOwnerAccount.this.finish();
                        break;

                    case R.id.ic_acc:
                        break;

                }
                return false;
            }
        });
    }

    public void AppOwnerLogout(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(Intent);
        FirebaseAuth.getInstance().signOut();
        this.finish();
    }

    public void buttonEditProfileView(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditProfile.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonEditSettingsView(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditSettings.class);
        startActivity(Intent);
        this.finish();
    }


    public void buttonEditPayoutView(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditPayout.class);
        startActivity(Intent);
        this.finish();
    }



}

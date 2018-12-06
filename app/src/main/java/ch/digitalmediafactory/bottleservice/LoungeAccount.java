package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Chris Gacu on 19/02/2018.
 */

public class LoungeAccount extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_account);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_dash:
                        Intent Intent0 = new Intent(LoungeAccount.this, LoungeDashboard.class);
                        startActivity(Intent0);
                        break;

                    case R.id.ic_inbox:
                        Intent Intent1 = new Intent(LoungeAccount.this, LoungeMessages.class);
                        startActivity(Intent1);
                        LoungeAccount.this.finish();
                        break;

                    case R.id.ic_loc:
                        Intent Intent2 = new Intent(LoungeAccount.this, LoungeLocation.class);
                        startActivity(Intent2);
                        LoungeAccount.this.finish();
                        break;

                    case R.id.ic_notif:
                        Intent Intent3 = new Intent(LoungeAccount.this, LoungeReserve.class);
                        startActivity(Intent3);
                        LoungeAccount.this.finish();
                        break;

                    case R.id.ic_acc:
                        break;

                }
                return false;
            }
        });
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

    public void buttonLoginPageView(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditSettings.class);
        startActivity(Intent);
        this.finish();
    }

    public void LoungeLogout(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), MainActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(Intent);
        this.finish();
    }
}

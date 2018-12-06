package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Chris Gacu on 06/03/2018.
 */

public class LoungeDashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lounge_dashboard);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
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
                        Intent Intent1 = new Intent(LoungeDashboard.this, LoungeMessages.class);
                        startActivity(Intent1);

                        break;

                    case R.id.ic_loc:
                        Intent Intent2 = new Intent(LoungeDashboard.this, LoungeLocation.class);
                        startActivity(Intent2);

                        break;

                    case R.id.ic_notif:
                        Intent Intent3 = new Intent(LoungeDashboard.this, LoungeReserve.class);
                        startActivity(Intent3);

                        break;

                    case R.id.ic_acc:
                        Intent Intent4 = new Intent(LoungeDashboard.this, LoungeAccount.class);
                        startActivity(Intent4);
                        break;

                }
                return false;
            }
        });
    }

    private void sendToStart(){
        Intent StartIntent = new Intent(LoungeDashboard.this, MainActivity.class);
        startActivity(StartIntent);
        finish();
    }

    public void LoungeOwnerReservation(View v){
        Intent StartIntent = new Intent(LoungeDashboard.this, LoungeOwnerReservation.class);
        startActivity(StartIntent);
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            sendToStart();
        }else if(currentUser != null){

        }



    }

}

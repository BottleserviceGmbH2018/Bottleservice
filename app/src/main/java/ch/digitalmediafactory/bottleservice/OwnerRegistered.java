package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class OwnerRegistered extends AppCompatActivity {

    private FirebaseUser mCurrentUser;

    private RecyclerView mLocationBarList;
    private DatabaseReference mBarDatabase;
    FirebaseRecyclerAdapter adapterLocationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_registered);
    }

    public void buttonOwnerRegisterDashboard(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeDashboard.class);
        startActivity(Intent);
        this.finish();
    }
}

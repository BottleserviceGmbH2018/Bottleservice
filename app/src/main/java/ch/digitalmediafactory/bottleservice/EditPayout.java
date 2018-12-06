package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditPayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_payout);
    }

    public void buttonEditPayoutAccount(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeAccount.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonEditBankAccount(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditBankAccount.class);
        startActivity(Intent);
        this.finish();
    }

}

package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditBankAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bank_account);
    }

    public void buttonEditPayout(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditPayout.class);
        startActivity(Intent);
        this.finish();
    }


    public void buttonEditPayoutOptionsAccount(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeAccount.class);
        startActivity(Intent);
        this.finish();
    }
}

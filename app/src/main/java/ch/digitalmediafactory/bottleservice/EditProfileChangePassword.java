package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EditProfileChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_change_password);
    }

    public void buttonEditSettingsBackView(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), EditSettings.class);
        startActivity(Intent);
        this.finish();
    }

    public void buttonEditSettingsBackViewAccounts(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), LoungeAccount.class);
        startActivity(Intent);
        this.finish();
    }
}

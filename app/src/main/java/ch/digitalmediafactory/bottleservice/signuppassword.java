package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ybs.passwordstrengthmeter.PasswordStrength;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signuppassword extends AppCompatActivity implements TextWatcher{
    private EditText etPassword;
    private ImageView bRegisterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppassword);




        etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPassword2 = (EditText) findViewById(R.id.etPassword2);
        bRegisterPassword = (ImageView) findViewById(R.id.bRegisterPassword);
        etPassword.addTextChangedListener(this);

        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);
        final String firstname = intent.getStringExtra("firstname");
        final String middlename = intent.getStringExtra("middlename");
        final String lastname = intent.getStringExtra("lastname");
        final String email = intent.getStringExtra("email");
        final String address = intent.getStringExtra("address");
        final String address2 = intent.getStringExtra("address2");
        final String city = intent.getStringExtra("city");
        final String postal = intent.getStringExtra("postal");


        bRegisterPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordstr = etPassword.getText().toString();
                PasswordStrength str = PasswordStrength.calculateStrength(passwordstr);
                if (etPassword.getText().toString().matches("")) {
                    etPassword.setError("Please Enter Your Password");
                    etPassword.requestFocus();
                    return;
                }
                else if (str.getText(signuppassword.this).equals("Weak")) {
                    etPassword.setError("Password must contains minimum 8 characters at least 1 number and special character");
                    etPassword.requestFocus();
                    return;
                }
                else if( ! etPassword.getText().toString().matches(etPassword2.getText().toString())) {
                    etPassword2.setError("Password do not match!");
                    etPassword2.requestFocus();
                    return;
                }
                else {
                    String password = etPassword.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), signupbirthday.class);
                    intent.putExtra("firstname", firstname);
                    intent.putExtra("middlename", middlename);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("user_type", user_type);
                    intent.putExtra("email", email);
                    intent.putExtra("address", address);
                    intent.putExtra("address2", address2);
                    intent.putExtra("city", city);
                    intent.putExtra("postal", postal);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });

    }

//    public static boolean isValidPassword(final String password) {
//
//        Pattern pattern;
//        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//        pattern = Pattern.compile(PASSWORD_PATTERN);
//        matcher = pattern.matcher(password);
//        return matcher.matches();
//
//    }

    public void buttonViewSignUpEmail(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), signupemail.class);
        startActivity(Intent);
        this.finish();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
    @Override
    public void beforeTextChanged(
            CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updatePasswordStrengthView(s.toString());
    }

    private void updatePasswordStrengthView(String password) {

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView strengthView = (TextView) findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Medium")) {
            bRegisterPassword.setClickable(true);
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Strong")) {
            bRegisterPassword.setClickable(true);
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
            bRegisterPassword.setClickable(true);
        }
    }

}

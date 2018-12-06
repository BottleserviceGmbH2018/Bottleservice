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

public class signupcompanypassword extends AppCompatActivity implements TextWatcher {
    private EditText etCompanyPassword;
    private ImageView bRegisterCompanyPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcompanypassword);

        Intent intent = getIntent();
        final int user_type = intent.getIntExtra("user_type", -1);
        final String companyname = intent.getStringExtra("companyname");
        final String companyownername = intent.getStringExtra("companyownername");
        final String companyemail = intent.getStringExtra("companyemail");
        etCompanyPassword = (EditText) findViewById(R.id.etCompanypassword);
        final EditText etCompanyPassword2 = (EditText) findViewById(R.id.etCompanypassword2);
        bRegisterCompanyPassword = (ImageView) findViewById(R.id.bRegisterCompanypassword);
        etCompanyPassword.addTextChangedListener(this);


        bRegisterCompanyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordstr = etCompanyPassword.getText().toString();
                PasswordStrength str = PasswordStrength.calculateStrength(passwordstr);
                if (etCompanyPassword.getText().toString().matches("")) {
                    etCompanyPassword.setError("Please Enter Your Password");
                    etCompanyPassword.requestFocus();
                    return;

                }else if (str.getText(signupcompanypassword.this).equals("Weak")) {
                    etCompanyPassword.setError("Password must contains minimum 8 characters at least 1 number and special character");
                    etCompanyPassword.requestFocus();
                    return;
                }else if( ! etCompanyPassword.getText().toString().matches(etCompanyPassword2.getText().toString())) {
                    etCompanyPassword2.setError("Password Do Not Match!");
                    etCompanyPassword2.requestFocus();
                    return;
                }else {
                    String companypassword = etCompanyPassword.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), signupcompanylocation.class);
                    intent.putExtra("user_type", user_type);
                    intent.putExtra("companyname", companyname);
                    intent.putExtra("companyownername", companyownername);
                    intent.putExtra("companyemail", companyemail);
                    intent.putExtra("companypassword", companypassword);
                    startActivity(intent);
                }
            }
        });

    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }

    public void buttonViewSignUpCompanyEmail(View v)
    {
        Intent Intent = new Intent(getApplicationContext(), signupcompanyemail.class);
        startActivity(Intent);
        this.finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updatePasswordStrengthView(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

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
            bRegisterCompanyPassword.setClickable(true);
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Strong")) {
            bRegisterCompanyPassword.setClickable(true);
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
            bRegisterCompanyPassword.setClickable(true);
        }
    }
}

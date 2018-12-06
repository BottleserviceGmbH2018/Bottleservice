package ch.digitalmediafactory.bottleservice;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AddSetLocationSpecificDetails extends AppCompatActivity {
    int counterGuest = 0;
    int counterPrice = 0;
    EditText tGuest, tPrice;
    Button bContinue;
    private DatabaseReference mDatabase, mDatabaseSpecific, mAppOwnerApproveDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set_location_specific_details);
        tGuest = (EditText) findViewById(R.id.tGuests);
        tPrice = (EditText) findViewById(R.id.tPrice);
        bContinue = (Button) findViewById(R.id.bContinuedetails);
        ImageButton ibPositiveGuest = (ImageButton) findViewById(R.id.ibPositiveGuest);
        ImageButton ibNegativeGuest = (ImageButton) findViewById(R.id.ibNegativeGuest);
        ImageButton ibPositivePrice = (ImageButton) findViewById(R.id.ibPositivePrice);
        ImageButton ibNegativePrice = (ImageButton) findViewById(R.id.ibNegativePrice);
        final EditText etSpecialTitle = (EditText) findViewById(R.id.etSpecialTitle);
        final EditText etSpecialArea = (EditText) findViewById(R.id.etSpecialArea);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        final String titlelocation = intent.getStringExtra("titlelocation");
        final String locationtype = intent.getStringExtra("locationtype");






        bContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String specialtitle = etSpecialTitle.getText().toString();
                String specialarea = etSpecialArea.getText().toString();
                String specialguest = tGuest.getText().toString();
                String specialprice = tPrice.getText().toString();

                if (etSpecialTitle.getText().toString().matches("") && etSpecialArea.getText().toString().matches("") ) {
                    etSpecialTitle.setError("Please The Title of Your Special Area");
                    etSpecialArea.setError("Please Describe Your Special Area");
                    etSpecialTitle.requestFocus();
                    return;
                }
                else if (etSpecialTitle.getText().toString().matches("")){
                    etSpecialTitle.setError("Please The Title of Your Special Area");
                    etSpecialTitle.requestFocus();
                    return;
                }
                else if (etSpecialArea.getText().toString().matches("")){
                    etSpecialArea.setError("Please Describe Your Special Area");
                    etSpecialArea.requestFocus();
                    return;
                }else {

                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();



                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Location_Specific").child(locationtype).child(uid).child(titlelocation).child(specialtitle);
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("locationTitle", specialtitle);
                    userMap.put("specficationDescription", specialarea);
                    userMap.put("specficationTitle", specialguest);
                    userMap.put("specficationPrice", specialprice);
                    userMap.put("specficationSlider", "default");
                    mDatabase.setValue(userMap);

                    mAppOwnerApproveDatabase = FirebaseDatabase.getInstance().getReference().child("location_req").child("gYMAHaVkJRhidNaIvcN8atFtawi2").child(titlelocation).child("locationspecific").child(specialtitle);
                    HashMap<String, String> userMapClient = new HashMap<>();
                    userMapClient.put("locationspecifictitle", specialtitle);
                    userMapClient.put("locationspecificarea", specialarea);
                    userMapClient.put("locationspecificguest", specialguest);
                    userMapClient.put("locationspecificprice", specialprice);
                    userMapClient.put("locationspecificphoto", "default");
                    mAppOwnerApproveDatabase.setValue(userMapClient);


                    mDatabaseSpecific = FirebaseDatabase.getInstance().getReference().child("locationspecific").child(locationtype).child(uid).child(titlelocation).child(specialtitle);
                    HashMap<String, String> userMapSpecific = new HashMap<>();
                    userMapSpecific.put("locationspecifictitle", specialtitle);
                    userMapSpecific.put("locationspecificarea", specialarea);
                    userMapSpecific.put("locationspecificguest", specialguest);
                    userMapSpecific.put("locationspecificprice", specialprice);
                    userMapSpecific.put("locationspecificphoto", "default");
                    mDatabaseSpecific.setValue(userMapSpecific);

                    Intent intent = new Intent(getApplicationContext(), AddSetLocationSpecificPhotos.class);
                    intent.putExtra("locationtype", locationtype);
                    intent.putExtra("specialtitle", specialtitle);
                    intent.putExtra("titlelocation", titlelocation);
                    startActivity(intent);

                    etSpecialTitle.setText("");
                    etSpecialArea.setText("");
                    tGuest.setText("0");
                    tPrice.setText("0");
                }
            }
        });


        ibPositiveGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                counterGuest++;
                if(counterGuest<0){
                    counterGuest=0;
                }
                if(Integer.parseInt(tGuest.getText().toString())>counterGuest){
                    counterGuest=Integer.parseInt(tGuest.getText().toString());
                    counterGuest++;
                }
                if(Integer.parseInt(tGuest.getText().toString())<counterGuest){
                    counterGuest=Integer.parseInt(tGuest.getText().toString());
                    counterGuest++;
                }
                if(counterGuest>0){
                    bContinue.setText("Next");
                    bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
                    bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));
                }
                tGuest.setText(Integer.toString(counterGuest));

                return true;
            }
        });

        ibNegativeGuest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                counterGuest--;
                if(counterGuest<0){
                    counterGuest=0;
                }
                if(Integer.parseInt(tGuest.getText().toString())>counterGuest){
                    counterGuest=Integer.parseInt(tGuest.getText().toString());
                    counterGuest--;
                }
                if(Integer.parseInt(tGuest.getText().toString())<counterGuest){
                    counterGuest=Integer.parseInt(tGuest.getText().toString());
                    counterGuest--;
                }
                if(counterGuest>0){
                    bContinue.setText("Next");
                    bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
                    bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));
                }
                tGuest.setText(Integer.toString(counterGuest));

                return true;
            }
        });
        ibPositivePrice.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                counterPrice++;
                if(counterPrice<0){
                    counterPrice=0;
                }
                if(Integer.parseInt(tPrice.getText().toString())>counterPrice){
                    counterPrice=Integer.parseInt(tPrice.getText().toString());
                    counterPrice++;
                }
                if(Integer.parseInt(tPrice.getText().toString())<counterPrice){
                    counterPrice=Integer.parseInt(tPrice.getText().toString());
                    counterPrice++;
                }
                if(counterPrice>0){
                    bContinue.setText("Next");
                    bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
                    bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));
                }
                tPrice.setText(Integer.toString(counterPrice));

                return true;
            }
        });

        ibNegativePrice.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                counterPrice--;
                if(counterPrice<0){
                    counterPrice=0;
                }
                if(Integer.parseInt(tPrice.getText().toString())>counterPrice){
                    counterPrice=Integer.parseInt(tPrice.getText().toString());
                    counterPrice--;
                }
                if(Integer.parseInt(tPrice.getText().toString())<counterPrice){
                    counterPrice=Integer.parseInt(tPrice.getText().toString());
                    counterPrice--;
                }
                if(counterPrice>0){
                    bContinue.setText("Next");
                    bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
                    bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));
                }
                tPrice.setText(Integer.toString(counterPrice));

                return true;
            }
        });
    }


    public void countINGuest(View view) {
        counterGuest++;
        if(counterGuest<0){
            counterGuest=0;
        }
        if(Integer.parseInt(tGuest.getText().toString())>counterGuest){
            counterGuest=Integer.parseInt(tGuest.getText().toString());
            counterGuest++;
        }
        if(Integer.parseInt(tGuest.getText().toString())<counterGuest){
            counterGuest=Integer.parseInt(tGuest.getText().toString());
            counterGuest++;
        }
        if(counterGuest>0){
            bContinue.setText("Next");
            bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
            bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));
        }
        tGuest.setText(Integer.toString(counterGuest));
    }


    public void countDownGuest(View view) {
        counterGuest--;
        if(counterGuest<0){
            counterGuest=0;
        }
        if(Integer.parseInt(tGuest.getText().toString())>counterGuest){
            counterGuest=Integer.parseInt(tGuest.getText().toString());
            counterGuest--;
        }
        if(Integer.parseInt(tGuest.getText().toString())<counterGuest){
            counterGuest=Integer.parseInt(tGuest.getText().toString());
            counterGuest--;
        }
        if(counterGuest>0){
            bContinue.setText("Next");
            bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
            bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));
        }
        tGuest.setText(Integer.toString(counterGuest));

    }

    public void countINPrice(View view) {
        counterPrice++;
        if(counterPrice<0){
            counterPrice=0;
        }
        if(Integer.parseInt(tPrice.getText().toString())>counterPrice){
            counterPrice=Integer.parseInt(tPrice.getText().toString());
            counterPrice++;
        }
        if(Integer.parseInt(tPrice.getText().toString())<counterPrice){
            counterPrice=Integer.parseInt(tPrice.getText().toString());
            counterPrice++;
        }
        if(counterPrice>0){
            bContinue.setText("Next");
            bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
            bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));
        }
        tPrice.setText(Integer.toString(counterPrice));
    }


    public void countDownPrice(View view) {
        counterPrice--;
        if(counterPrice<0){
            counterPrice=0;
        }
        if(Integer.parseInt(tPrice.getText().toString())>counterPrice){
            counterPrice=Integer.parseInt(tPrice.getText().toString());
            counterPrice--;
        }
        if(Integer.parseInt(tPrice.getText().toString())<counterPrice){
            counterPrice=Integer.parseInt(tPrice.getText().toString());
            counterPrice--;
        }
        if(counterPrice>0){
            bContinue.setText("Next");
            bContinue.setTextColor(getResources().getColor(R.color.colorWhite));
            bContinue.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.next_bg));
        }
        tPrice.setText(Integer.toString(counterPrice));

    }




}
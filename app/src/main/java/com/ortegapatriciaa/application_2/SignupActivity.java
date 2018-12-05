package com.ortegapatriciaa.application_2;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SignupActivity extends AppCompatActivity {

    EditText sname, semail, sbirthdate, susername, spassword, sconpassword;
    Button btnSignup;

    private Calendar cal;
    private int day;
    private int month;
    private int year;

    DatabaseReference mDatabaseReference;
    FirebaseDatabase firebaseDatabase;
    private FirebaseAuth auth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Signup");

        progressDialog = new ProgressDialog(this);

        FirebaseApp.initializeApp(SignupActivity.this);
        auth = FirebaseAuth.getInstance();

        sname = (EditText) findViewById(R.id.editText_sname);
        semail = (EditText) findViewById(R.id.editText_semail);
        sbirthdate = (EditText) findViewById(R.id.editText_sbirthdate);
        susername = (EditText) findViewById(R.id.editText_susername);
        spassword = (EditText) findViewById(R.id.editText_spassword);
        sconpassword = (EditText) findViewById(R.id.editText_sconpassword);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("accounts_1");

        sbirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = sname.getText().toString().trim();
                final String email = semail.getText().toString().trim();
                final String birthdate = sbirthdate.getText().toString().trim();
                final String username = susername.getText().toString().trim();
                final String password = spassword.getText().toString().trim();
                final String conpassword = sconpassword.getText().toString().trim();

                final String userID = mDatabaseReference.push().getKey();

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(birthdate)){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(conpassword)){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!TextUtils.equals(password, conpassword)){
                    Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.length() < 8){
                    Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Processing, Please wait ...");
                progressDialog.show();

                mDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(username).exists()){
                            progressDialog.dismiss();
                            new AlertDialog.Builder(SignupActivity.this)
                                    .setTitle("Registration Failed!")
                                    .setMessage("Email Already Exist. Please try again!")
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //
                                        }
                                    }).show();
                        }else{
                            progressDialog.dismiss();
                            AccountInfo accountInfo = new AccountInfo(userID, name, email, birthdate, username, password);
                            //mDatabaseReference.child(uname).setValue(accountInfo);
                            mDatabaseReference.child(username).setValue(accountInfo);
                            Toast.makeText(SignupActivity.this, "User Successfully Registered!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public void  dateDialog(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sbirthdate.setText(new StringBuilder().append(month + 1).append("/").append(dayOfMonth).append("/").append(year));
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(this, listener, year, month, day);
        dialog.show();
    }
}

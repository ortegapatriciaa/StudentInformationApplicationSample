
package com.ortegapatriciaa.application_2;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ortegapatriciaa.application_2.Common.Common;

import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;
    TextView signup;
    private String check = "A sentence with a error in the Hitchhiker's Guide tot he Galaxy";

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        email = (EditText) findViewById(R.id.editText_email);
        password = (EditText) findViewById(R.id.editText_password);

        signup = (TextView) findViewById(R.id.textview_signup);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        progressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("accounts_1");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sign_email = email.getText().toString().trim();
                final String sign_passsword = password.getText().toString().trim();

                if (TextUtils.isEmpty(sign_email)){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(sign_passsword)){
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.setMessage("Please wait ...");
                progressDialog.show();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(sign_email).exists()) {
                            progressDialog.dismiss();
                            AccountInfo accountInfo = dataSnapshot.child(sign_email).getValue(AccountInfo.class);
                            if (accountInfo.getPassword().equals(sign_passsword)) {
                                Toast.makeText(LoginActivity.this, "Successfully Login",
                                        Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                Common.currentUser = accountInfo;
                                startActivity(i);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Login Failed!")
                                        .setMessage(getString(R.string.auth_failed))
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //
                                            }
                                        }).show();
                            }
                        }else{
                            progressDialog.dismiss();
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Login Failed!")
                                    .setMessage(getString(R.string.auth_failed1))
                                    .setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}

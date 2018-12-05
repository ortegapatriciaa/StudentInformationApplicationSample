package com.ortegapatriciaa.application_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ortegapatriciaa.application_2.Common.Common;

public class EditActivity extends AppCompatActivity {

    EditText ename, eemail, ebirthdate, epassword;
    Button btnUpdate;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ename = (EditText) findViewById(R.id.editName);
        eemail = (EditText) findViewById(R.id.editEmail);
        ebirthdate = (EditText) findViewById(R.id.editBirthdate);
        epassword = (EditText) findViewById(R.id.editPassword);

        btnUpdate = (Button) findViewById(R.id.btnupdate);

        ename.setText(Common.currentUser.getName());
        eemail.setText(Common.currentUser.getEmail());
        ebirthdate.setText(Common.currentUser.getBirthdate());
        epassword.setText(Common.currentUser.getPassword());

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("accounts_1");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountInfo accountInfo = new AccountInfo(
                        Common.currentUser.getUserid(),
                        ename.getText().toString(),
                        eemail.getText().toString(),
                        ebirthdate.getText().toString(),
                        Common.currentUser.getUsername(),
                        epassword.getText().toString()
                );

                databaseReference.child(Common.currentUser.getUsername()).setValue(accountInfo);

                Toast.makeText(EditActivity.this, "Account Info updated!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(EditActivity.this, MainActivity.class));
            }


        });
    }
}

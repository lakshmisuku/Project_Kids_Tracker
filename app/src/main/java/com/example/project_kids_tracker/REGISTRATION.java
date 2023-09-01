package com.example.project_kids_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kotlin.Suppress;

public class REGISTRATION extends AppCompatActivity {
    TextView PName, PMail, PMob, PNoC, Pass, Confirm, User, CName;
    EditText editPName, editPMail, editPMob, editPNoC, editCName, editPass, editConfirm, editUser;
    /*RadioGroup rGroup1;
    RadioButton radioButton1,rButton1,rButton2;
    String gen;*/
    Button Button;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick() {
        PName = (TextView) findViewById(R.id.textView3);
        PMail = (TextView) findViewById(R.id.textView4);
        PMob = (TextView) findViewById(R.id.textView5);
        User = (TextView) findViewById(R.id.textView6);
        Pass = (TextView) findViewById(R.id.textView7);
        Confirm = (TextView) findViewById(R.id.textView8);
        PNoC = (TextView) findViewById(R.id.textView9);
        CName = (TextView) findViewById(R.id.textView11);

        editPName = (EditText) findViewById(R.id.editText1);
        editPMail = (EditText) findViewById(R.id.editText2);
        editPMob = (EditText) findViewById(R.id.editText3);
        editUser = (EditText) findViewById(R.id.editText4);
        editPass = (EditText) findViewById(R.id.editText5);
        editConfirm = (EditText) findViewById(R.id.editText6);
        editPNoC = (EditText) findViewById(R.id.editText7);
        editCName = (EditText) findViewById(R.id.editText8);

        /*rGroup1 = (RadioGroup) findViewById(R.id.radio1);

        rButton1 = (RadioButton) findViewById(R.id.radioBoy);
        rButton2 = (RadioButton) findViewById(R.id.radioGirl);*/

        Button = (Button) findViewById(R.id.btn1);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kids-tracker-d6c5a-default-rtdb.firebaseio.com/");

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pname = editPName.getText().toString();
                String pmail = editPMail.getText().toString();
                String pmob = editPMob.getText().toString();
                String pnoc = editPNoC.getText().toString();
                String cname = editCName.getText().toString();
                String uname = editUser.getText().toString();
                String pass = editPass.getText().toString();
                String confirm = editConfirm.getText().toString();

                if (pname.isEmpty() || pmail.isEmpty() || pmob.isEmpty() || pnoc.isEmpty() || cname.isEmpty()) {
                    Toast.makeText(REGISTRATION.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
                }else {
                    if (pass.equals(confirm)) {
                        databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(pmob)) {
                                    Toast.makeText(REGISTRATION.this, "PHONE NUMBER is already Registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    databaseReference.child("Profile").child(pmob).child("Username").setValue(uname);
                                    databaseReference.child("Profile").child(pmob).child("Name").setValue(pname);
                                    databaseReference.child("Profile").child(pmob).child("Email").setValue(pmail);
                                    databaseReference.child("Profile").child(pmob).child("Contact").setValue(pmob);
                                    databaseReference.child("Profile").child(pmob).child("Number of child").setValue(pnoc);
                                    databaseReference.child("Profile").child(pmob).child("Password").setValue(pass);
                                    databaseReference.child("Profile").child(pmob).child("Child name").setValue(cname);
                                    databaseReference.child("Profile").child(pmob).child("Password Confirm").setValue(confirm);
                                    Toast.makeText(REGISTRATION.this, "User Successfully Registered", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(REGISTRATION.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        Toast.makeText(REGISTRATION.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
}
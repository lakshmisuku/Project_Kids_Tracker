package com.example.project_kids_tracker;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText edituser, editpass;
    TextView click,signup;

    Button button;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButtonClick();
    }

    public void addListenerOnButtonClick() {
        edituser = (EditText) findViewById(R.id.editText1);
        editpass = (EditText) findViewById(R.id.editText2);
        click = (TextView) findViewById(R.id.textView1);
        signup = (TextView) findViewById(R.id.textView2);

        button = (Button) findViewById(R.id.btn1);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://kids-tracker-d6c5a-default-rtdb.firebaseio.com/");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = edituser.getText().toString();
                String pass = editpass.getText().toString();

                if (uname.isEmpty() && pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    if (uname.equals("Admin")) {

                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(uname)) {
                                    final String getPass = snapshot.child(uname).child("Password").getValue(String.class);

                                    if (getPass.equals(pass)) {
                                        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                                        i.putExtra("Username", uname);
                                        startActivity(i);
                                        Toast.makeText(MainActivity.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Log.d(TAG, "onDataChange: " + snapshot.toString());
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else {
                        databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(uname)) {
                                    final String getPassword = snapshot.child(uname).child("Password").getValue(String.class);
                                    if (getPassword.equals(pass)) {
                                        Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                                        i.putExtra("username", uname);
                                        startActivity(i);
                                        Toast.makeText(MainActivity.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), forgetpassword.class);
                startActivity(i);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), REGISTRATION.class);
                startActivity(i);
            }
        });
    }
}
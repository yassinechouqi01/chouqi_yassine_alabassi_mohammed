package com.example.myprj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myprj.Homeactivity;
import com.example.myprj.R;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnlogin;
    TextView btnsignup;
    com.example.myprj.DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize DB object
        DB = new com.example.myprj.DBhelper(this);

        email = (EditText) findViewById(R.id.email1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.butsignin1);
        btnsignup=(TextView) findViewById(R.id.butsignup1);


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the sign-up activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String em = email.getText().toString();
                String pass = password.getText().toString();

                if (em.equals("") || pass.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkpassword(em, pass);
                    if (checkuserpass) {
                        Toast.makeText(LoginActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Homeactivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
package com.example.myprj;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myprj.DBhelper;
import com.example.myprj.Homeactivity;
import com.example.myprj.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView gb;
    EditText password, email, repassword;
    TextView signin;
    Button signup;
    DBhelper DB;
    Spinner cityS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gb = findViewById(R.id.gm);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        cityS= findViewById(R.id.city1);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.butsignup);
        signin = findViewById(R.id.butsignin);
        DB = new DBhelper(this);


        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String em = email.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String city = cityS.getSelectedItem().toString();
                if (em.equals("") || pass.equals("") || repass.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(repass)) {
                        boolean checkuser = DB.checkusername(em);
                        if (!checkuser) {
                            boolean insert = DB.insertdata(em, pass);
                            if (insert) {
                                Toast.makeText(MainActivity.this, "Registration successfully completed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Homeactivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "User already exists! Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    void signIn() {
        Intent signInintent = gsc.getSignInIntent();
        startActivityForResult(signInintent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                navigateToHomeActivity(); // Navigate to Homeactivity instead of formule
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToHomeActivity() {
        finish();
        Intent intent = new Intent(MainActivity.this, Homeactivity.class); // Change the intent destination
        startActivity(intent);
    }
}

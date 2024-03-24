package com.example.myprj;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class formule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formule);

        String city = getIntent().getStringExtra("city");
        int formCount = getIntent().getIntExtra("formCount", 0);
        TextView cityTextView = findViewById(R.id.cityTextView);
        TextView formCountTextView = findViewById(R.id.formCountTextView);

        cityTextView.setText("Dans la ville : " + city);
        formCountTextView.setText("Nombre des anonnces : " + formCount);
    }
}







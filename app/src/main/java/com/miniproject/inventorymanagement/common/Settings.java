package com.miniproject.inventorymanagement.common;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.miniproject.inventorymanagement.R;

public class Settings extends AppCompatActivity {

    CardView aboutUs;
    TextView txtAU,txtCU,txtTC,txtfb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("  Setting");

        txtAU = findViewById(R.id.txtau);
        txtCU = findViewById(R.id.txtcu);
        txtTC = findViewById(R.id.txttc);
        txtfb=findViewById(R.id.txtfb);
        Button logutButton = findViewById(R.id.logoutButton);


        txtAU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://lathiyaparth.github.io/aboutUs/";

                // Create an Intent to open a web browser with the specified URL.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the web browser activity.
                startActivity(intent);
            }
        });

        txtCU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "https://lathiyaparth.github.io/contactUs/";

                // Create an Intent to open a web browser with the specified URL.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the web browser activity.
                startActivity(intent);


            }
        });


        txtTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "https://lathiyaparth.github.io/termcondion/";

                // Create an Intent to open a web browser with the specified URL.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the web browser activity.
                startActivity(intent);


            }
        });

        txtfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "https://lathiyaparth.github.io/feedback/";

                // Create an Intent to open a web browser with the specified URL.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the web browser activity.
                startActivity(intent);


            }
        });

        logutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Settings.this, UserTypeActivity.class));
        });
    }
}
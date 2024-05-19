package com.example.memorization.activities;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.memorization.DBHelper;
import com.example.memorization.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

public class Memorizations extends AppCompatActivity
{

    MaterialButton btn_qr, btn_pager, btn_pdf, btn_scanner, btn_profile;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_memorizations);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();
        setListeners();


        Cursor cursor = dbHelper.getAccount();
        cursor.moveToFirst();
        if(cursor.getCount() < 1)
        {
            embedAccount();
        }


    }

    private void initValues()
    {
        btn_qr = findViewById(R.id.btn_qr);
        btn_pager = findViewById(R.id.btn_view_pager);
        btn_pdf = findViewById(R.id.btn_pdf);
        btn_profile = findViewById(R.id.btn_profile);
        btn_scanner = findViewById(R.id.btn_scanner);

        dbHelper = new DBHelper(this);
    }

    private void setListeners()
    {
        btn_qr.setOnClickListener(qr -> {
            Intent intent = new Intent(Memorizations.this, QRCodeActivity.class);
            startActivity(intent);
        });

        btn_pager.setOnClickListener(pager -> {
            Intent intent = new Intent(Memorizations.this, ViewPagerActivity.class);
            startActivity(intent);
        });

        btn_pdf.setOnClickListener(pdf -> {
            Intent intent = new Intent(Memorizations.this, PdfActivity.class);
            startActivity(intent);
        });

        btn_profile.setOnClickListener(profile -> {
            Intent intent = new Intent(Memorizations.this, ProfilePictureActivity.class);
            startActivity(intent);
        });

        btn_scanner.setOnClickListener(scan -> {
            Intent intent = new Intent(Memorizations.this, QRScannerActivity.class);
            startActivity(intent);
        });

    }

    private void displayDialog()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Memorizations.this)
                        .setTitle("Hello World")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(Memorizations.this, "Done", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }, 2000);
    }
    private void embedAccount()
    {
        if(dbHelper.addAccount("lee18", "1234", null))
        {
            Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Add failed", Toast.LENGTH_SHORT).show();
        }
    }



}
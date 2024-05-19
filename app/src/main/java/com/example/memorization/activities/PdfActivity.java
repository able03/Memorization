package com.example.memorization.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.PagerAdapter;


import com.example.memorization.R;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfActivity extends AppCompatActivity
{

    EditText et_uname, et_pass;
    MaterialButton btn_create, btn_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pdf);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();
        setListeners();
    }

    private void initValues()
    {
        et_uname = findViewById(R.id.et_username);
        et_pass = findViewById(R.id.et_password);

        btn_create = findViewById(R.id.btn_create_pdf);
        btn_view = findViewById(R.id.btn_view_pdf);
    }


    private void setListeners()
    {
        btn_create.setOnClickListener(create -> {
            String uname = et_uname.getText().toString().trim();
            String pass = et_pass.getText().toString().trim();
            createPdf(uname, pass, "test");
        });

        btn_view.setOnClickListener(view -> {
            viewPdf("test");
        });
    }


    private void createPdf(String uname, String pass, String fileName)
    {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setTextSize(10);
        canvas.drawText(uname, 10, 60, paint);

        paint.setColor(Color.BLUE);
        paint.setTextSize(10);
        canvas.drawText(pass, 10, 80, paint);

        document.finishPage(page);

        String dir = Environment.getExternalStorageDirectory().getPath() + "/Download/";
        File file = new File(dir, fileName);

        try
        {
            document.writeTo(new FileOutputStream(file));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void viewPdf(String fileName)
    {
        String dir = Environment.getExternalStorageDirectory().getPath() + "/Download/";
        File file = new File(dir, fileName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent chooser = Intent.createChooser(intent, "Open PDF");
        startActivity(chooser);
    }






}
package com.example.memorization.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.memorization.R;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;

public class QRCodeActivity extends AppCompatActivity
{

    MaterialButton button;
    ImageView imageView;
    EditText editText;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(editText.length() > 0)
                {
                    bitmap = encodeAsBitmap(editText.getText().toString());
                    imageView.setImageBitmap(bitmap);
                }
            }
        });

    }

    private void initValues()
    {
        button = findViewById(R.id.btn);
        editText = findViewById(R.id.et);
        imageView = findViewById(R.id.iv);
    }

    private Bitmap encodeAsBitmap(String str)
    {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = null;

        try
        {
            matrix = writer.encode(str, BarcodeFormat.QR_CODE, 400, 400);
        } catch (WriterException e)
        {
            throw new RuntimeException(e);
        }

        int w = matrix.getWidth();
        int h = matrix.getHeight();
        int[] pixels = new int[w*h];
        for(int y=0; y<h; y++)
        {
            for(int x=0; x<w; x++)
            {
                pixels[y * w + x] = matrix.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels,0, w, 0, 0, w, h);
        return bitmap;

    }

    private byte[] bitmapToBytesArray(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private Bitmap bytesArrayToBitmap(byte[] bb)
    {
        return BitmapFactory.decodeByteArray(bb, 0, bb.length);
    }



}
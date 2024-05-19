package com.example.memorization.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.memorization.DBHelper;
import com.example.memorization.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;

public class ProfilePictureActivity extends AppCompatActivity
{

    ImageView iv;
    ImageButton ib_add_photo;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_picture);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();
        setListener();


        Cursor cursor = dbHelper.getAccount();

        if(cursor.moveToFirst())
        {
            byte[] bb = cursor.getBlob(3);
            if(bb != null)
            {
                iv.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));
            }
        }


        dbHelper.close();

    }


    private void initValues()
    {
        iv = findViewById(R.id.iv_profile);
        ib_add_photo = findViewById(R.id.ib_add_photo);

        dbHelper = new DBHelper(this);
    }

    private void setListener()
    {
        ib_add_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ImagePicker.with(ProfilePictureActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1024, 1024)
                        .start();;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        dbHelper = new DBHelper(this);
        if(resultCode == Activity.RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            if(uri != null)
            {
                iv.setImageURI(uri);

                BitmapDrawable drawable = (BitmapDrawable) iv.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bb = stream.toByteArray();

                dbHelper.updateAccount(1, bb);
            }
        }
        else if(resultCode == ImagePicker.RESULT_ERROR)
        {
            Log.d("Debugging", String.valueOf(ImagePicker.getError(data)));
        }
        else
        {
            Log.d("Debugging", "Task cancelled");
        }

        dbHelper.close();
    }
}
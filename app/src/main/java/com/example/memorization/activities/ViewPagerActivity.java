package com.example.memorization.activities;

import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.memorization.FixedSpeedScroller;
import com.example.memorization.R;
import com.example.memorization.ViewPagerAdapter;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class ViewPagerActivity extends AppCompatActivity
{

    ViewPager viewPager;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_pager);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValues();
        setViewPager();
    }

    private void initValues()
    {
        viewPager = findViewById(R.id.view_pager);
    }

    private void setViewPager()
    {
        int[] images = {R.drawable.ic_banner_1, R.drawable.ic_banner_2, R.drawable.ic_banner_3};
        adapter = new ViewPagerAdapter(this, images);
        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                viewPager.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % images.length);
                    }
                });
            }
        }, 3000, 6000);


        try
        {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, new FixedSpeedScroller(this, new AccelerateInterpolator()));
        } catch (NoSuchFieldException | IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }

    }

}
package com.example.memorization;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class FixedSpeedScroller extends Scroller
{
    final int mDuration = 500;
    public FixedSpeedScroller(Context context)
    {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator)
    {
        super(context, interpolator);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel)
    {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy)
    {
        super.startScroll(startX, startY, dx, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration)
    {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}

package com.gunhansancar.android.animbutton;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageButton;

/**
 * Created by Günhan on 17.08.2015.
 */
public class AnimButton extends ImageButton {
    private static final Interpolator interpolator = new AccelerateDecelerateInterpolator();

    public static final int FIRST_STATE = 1;
    public static final int SECOND_STATE = 2;

    private Drawable firstDrawable;
    private Drawable secondDrawable;

    private int state = FIRST_STATE;
    private int total = 100;
    private int duration = 200;
    private boolean init = false;

    public AnimButton(Context context) {
        super(context);
    }

    public AnimButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnimButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AnimButton, 0, 0);

        int first = array.getResourceId(R.styleable.AnimButton_first, -1);
        int second = array.getResourceId(R.styleable.AnimButton_second, -1);
        duration = array.getInteger(R.styleable.AnimButton_duration, duration);

        if (array != null) {
            array.recycle();
        }

        if (first > 0 && second > 0) {
            init = true;

            Resources resources = context.getResources();
            firstDrawable = resources.getDrawable(first);
            secondDrawable = resources.getDrawable(second);

            setImageDrawable(firstDrawable);
        }
    }

    public void goToState(int state) {
        if (!init || this.state == state) return;

        switch (state) {
            case FIRST_STATE:
                animate(firstDrawable, secondDrawable);
                break;
            case SECOND_STATE:
                animate(secondDrawable, firstDrawable);
                break;
        }

        this.state = state;
    }

    private Drawable makeInsetDrawable(Drawable drawable, int inset) {
        return new InsetDrawable(drawable, inset, inset, inset, inset);
    }

    private void animate(final Drawable from, final Drawable to) {
        ValueAnimator animator = ValueAnimator.ofInt(0, total);
        animator.setDuration(duration);
        animator.setInterpolator(interpolator);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                int left = total - value;

                Drawable firstInset = makeInsetDrawable(from, left);
                Drawable secondInset = makeInsetDrawable(to, value);

                LayerDrawable layer = new LayerDrawable(new Drawable[]{firstInset, secondInset});
                setImageDrawable(layer);
            }
        });

        animator.start();
    }
}

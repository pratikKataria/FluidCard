package com.tricky_tweaks.fluidcard;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.tricky_tweaks.fluidcard.databinding.ActivityMainBinding;
import static com.tricky_tweaks.fluidcard.R.animator.fade_translate;

/*
* created by pratik katariya
* 3 jul 2020
* */

public class MainActivity extends AppCompatActivity {

    boolean firstRun = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.link.setPaintFlags(binding.link.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.link.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(binding.link.getText().toString()));
            startActivity(intent);
        });

        ValueAnimator bottomImageValueAnimator = ObjectAnimator.ofFloat(binding.avdBottom, "translationY", 262);
        bottomImageValueAnimator.setDuration(1000);
        bottomImageValueAnimator.setInterpolator(new OvershootInterpolator());


        AnimatorSet topAnimationSet = new AnimatorSet();
        topAnimationSet.play(valueAnimatorBuilder(binding.materialMaterialShow))
                .with(valueAnimatorBuilder(binding.avdUp));


        AnimatorSet bottomAnimationSet = new AnimatorSet();
        bottomAnimationSet.play(bottomImageValueAnimator).with(animatorBuilder(binding.textViewBottom)).with(animatorBuilder(binding.textViewBottom2));


        binding.materialMaterialShow.setOnClickListener(v -> {

            if (firstRun) {
                Drawable d = binding.avdUp.getDrawable();
                Drawable drawable = binding.avdBottom.getDrawable();
                if (d instanceof AnimatedVectorDrawable) {
                    AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
                    AnimatedVectorDrawable avdBottom = (AnimatedVectorDrawable) drawable;

                    avd.start();
                    avdBottom.start();

                    bottomAnimationSet.start();
                    topAnimationSet.start();
                    binding.imageExpandIcon.animate().rotation(180F).setDuration(500).start();

                    new Handler().postDelayed(topAnimationSet::reverse, 300);
                }
                firstRun = false;
            } else {
                bottomAnimationSet.reverse();
                binding.imageExpandIcon.animate().rotation(0F).setDuration(500).start();
                firstRun = true;
            }
        });
    }

    public ValueAnimator valueAnimatorBuilder(View view) {
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(view, "translationY", 20);
        valueAnimator.setDuration(800);
        valueAnimator.setInterpolator(new LinearInterpolator());
        return valueAnimator;
    }


    public Animator animatorBuilder(View targetView) {
        Animator textAnimator = AnimatorInflater.loadAnimator(this, fade_translate);
        textAnimator.setTarget(targetView);
        return textAnimator;
    }

    /*git merge practice via android studio*/
    /*git merge 2 practice via android studio*/
    /*git created new branch from try via android studio*/
}
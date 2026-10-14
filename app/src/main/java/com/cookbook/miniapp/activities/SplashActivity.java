package com.cookbook.miniapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.cookbook.miniapp.R;

/**
 * SplashActivity - First screen shown when the app launches.
 * Displays the app logo with fade animation, then navigates to MainActivity after 2.5 seconds.
 * Uses finish() so user cannot navigate back to splash screen.
 */
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500; // 2.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find views
        ImageView imgLogo = findViewById(R.id.imgSplashLogo);
        TextView tvAppName = findViewById(R.id.tvSplashAppName);
        TextView tvTagline = findViewById(R.id.tvSplashTagline);

        // Apply animations to make splash screen lively
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(1000);
        imgLogo.startAnimation(fadeIn);
        tvAppName.startAnimation(fadeIn);
        tvTagline.startAnimation(fadeIn);

        // Navigate to MainActivity after the splash duration
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close splash so back button doesn't return here
        }, SPLASH_DURATION);
    }
}

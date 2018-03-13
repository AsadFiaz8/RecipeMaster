package com.example.asadfiaz.recepieapp;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    /** Called when the activity is first created. */
    Thread splashTread;
    TextView txtRecipeMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        txtRecipeMaster= (TextView) findViewById(R.id.txtRecepie);
        Typeface font=Typeface.createFromAsset(getAssets(),"fonts/kottaone-regular.ttf");
        txtRecipeMaster.setTypeface(font);

        StartAnimations();
    }
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView recepieIcon = (ImageView) findViewById(R.id.recepieIcon);
        recepieIcon.clearAnimation();
        recepieIcon.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                } catch (InterruptedException e) {
                    // do nothing
                }

            }
        };
        splashTread.start();

    }

}

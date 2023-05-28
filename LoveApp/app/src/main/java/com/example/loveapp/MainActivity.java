package com.example.loveapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
public class MainActivity extends AppCompatActivity {

    private ImageButton Start;
    private ImageButton Cubes;
    int o;

    //number of questions!!!!!!!!!!!!!!!!!!!!!!!!LOOK AT CAREFULLY!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private int N =8;
    //the arrays includes all poses
    public Pose[] pose = new Pose[N];
    private String str;
    String [] strSplit;
    public static int randInt(int max) {

        Random r = new Random();
        int x = r.nextInt(max+1);
        return x;
    }

    public void addElementsToArrayPoses()  {

        //////////////////////////ЧТЕНИЕ ФАЙЛА СДЕЛАТЬ//////////////////////////////////////////////////
        try{
            InputStream inStr = getAssets().open("poses.txt");
            int size = inStr.available();
            byte[] buffer = new byte[size];
            inStr.read(buffer);
            str = new String(buffer);
            strSplit = str.split("\n");
            o = strSplit.length;
            N=o;

            for(int i = 0; i < o; i++) {
                String[] sHelp;
                sHelp = strSplit[i].split("\\|");
                //LOOK AT LAST STRING AT FILE!!!!!WARNING!!!!! \n should be last symbol at the string in person.txt
                pose[i] = new Pose(sHelp[0],sHelp[1], sHelp[2],sHelp[3].substring(0, sHelp[3].length() - 1));


            }



        } catch (IOException e) {

            throw new RuntimeException(e);
        }





    }

    void changeImage(){


        String uri = "@drawable/" + pose[randInt(N-1)].PoseImage;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        ImageView imageCurrent = findViewById(R.id.imageView);
        Drawable res = getResources().getDrawable(imageResource);
        imageCurrent.setImageDrawable(res);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black_1));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black_1));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        }

        //quiz will be started after click "Приступить"
        Start = findViewById(R.id.Start);
        Start.setOnClickListener(new View.OnClickListener() {


            @SuppressLint("WrongViewCast")
            public void onClick(View view) {



                //change status bar to black
                setContentView(R.layout.random_poses);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.black_1));
                    getWindow().setNavigationBarColor(getResources().getColor(R.color.black_1));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                }

                addElementsToArrayPoses();


                Cubes = findViewById(R.id.Cubes);
                Cubes.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        changeImage();


                    }
                });
                }

        });
    }
}
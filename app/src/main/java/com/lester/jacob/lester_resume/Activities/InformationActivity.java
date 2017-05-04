package com.lester.jacob.lester_resume.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lester.jacob.lester_resume.R;

public class InformationActivity extends BaseActivity{
    private ImageView bioImage;
    private Button prevBtn;
    private Button nextBtn;
    private Button viewSkillsBtn;
    private String imageTag = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_information);

        bioImage = (ImageView) findViewById(R.id.bioImage);
        prevBtn = (Button) findViewById(R.id.prevBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        viewSkillsBtn = (Button) findViewById(R.id.viewSkillsBtn);
        setImage();
        prevBtn.setEnabled(false);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageTag = bioImage.getTag().toString();
                if (imageTag.equals("1")) {
                    prevBtn.setEnabled(false);
                } else {
                    changeImage(-1);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageTag = bioImage.getTag().toString();
                if (imageTag.equals("3")) {
                    nextBtn.setEnabled(false);
                } else {
                    changeImage(1);
                }

            }
        });

        viewSkillsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SkillsActivity.class));
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("currentImage", imageTag);
        super.onSaveInstanceState(outState);
    }
    
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        imageTag = savedInstanceState.getString("currentImage");
        setImage();
        setButtonEnabled();
    }

    public void changeImage(int btnClicked) {
        prevBtn.setEnabled(true);
        nextBtn.setEnabled(true);
        imageTag = Integer.toString(Integer.parseInt(imageTag) + btnClicked);
        setButtonEnabled();
        bioImage.setTag(imageTag);
        setImage();
    }

    public void setImage() {
        Resources res = getResources();
        int resID = res.getIdentifier("slider" + imageTag , "drawable", getPackageName());
        bioImage.setTag(imageTag);
        bioImage.setImageResource(resID);
    }

    public void setButtonEnabled() {
        switch (imageTag) {
            case "1":
                prevBtn.setEnabled(false);
                nextBtn.setEnabled(true);
                break;
            case "3":
                nextBtn.setEnabled(false);
                prevBtn.setEnabled(true);
                break;
            default:
                prevBtn.setEnabled(true);
                nextBtn.setEnabled(true);
                break;
        }
    }

}


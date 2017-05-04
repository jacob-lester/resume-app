package com.lester.jacob.lester_resume.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.lester.jacob.lester_resume.Fragments.SettingsFragment;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, InformationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}

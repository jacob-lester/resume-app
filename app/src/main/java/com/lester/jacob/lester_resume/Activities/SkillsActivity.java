package com.lester.jacob.lester_resume.Activities;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lester.jacob.lester_resume.Classes.Skill;
import com.lester.jacob.lester_resume.Classes.SkillDB;
import com.lester.jacob.lester_resume.Fragments.AddSkillFragment;
import com.lester.jacob.lester_resume.Fragments.EditSkillFragment;
import com.lester.jacob.lester_resume.Fragments.SkillDetailFragment;
import com.lester.jacob.lester_resume.Fragments.SkillsFragment;
import com.lester.jacob.lester_resume.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class SkillsActivity extends BaseActivity implements SkillsFragment.SkillsInteractionListener,
        SkillDetailFragment.SkillDetailInteractionListener, EditSkillFragment.EditSkillInteractionListener,
        AddSkillFragment.AddSkillInteractionListener {

    private static List<Skill> skills;
    private SkillDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skills);

        db = new SkillDB(this);
        Gson gson = new Gson();
        String jsonOutput = jsonToStringFromAssetFolder(this);
        Type listType = new TypeToken<List<Skill>>(){}.getType();
        skills = (List<Skill>) gson.fromJson(jsonOutput, listType);

        if (findViewById(R.id.skills_container) != null) {
            if(savedInstanceState != null) {
                return;
            }
            SkillsFragment skillsFragment = new SkillsFragment();
            skillsFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.skills_container, skillsFragment).commit();
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSkillSelected(int id) {
        // The user selected a skill from  the SkillsFragment
        SkillDetailFragment detailFrag = (SkillDetailFragment) getSupportFragmentManager().findFragmentById(R.id.skill_detail_fragment);

        if (detailFrag != null) {
            // If SkillDetail frag is available, we're in two-pane layout...

            // Call a method in the SkillDetailFragment to update its content
            detailFrag.updateSkillDetailView(db.getSkill(id).getDesc());
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected skill detail
            SkillDetailFragment newFragment = new SkillDetailFragment();

            Bundle args = new Bundle();

            // TODO pass in skill details here

            args.putString(SkillDetailFragment.ARG_DETAILS, db.getSkill(id).getDesc());

            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the skills_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.skills_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @Override
    public void addSkill() {
        getSupportFragmentManager().beginTransaction().replace(R.id.skills_container, new AddSkillFragment()).commit();
    }


    @Override
    public void updateSkill() {
        SkillsFragment skillsFragment = new SkillsFragment();
        skillsFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.skills_container, skillsFragment).commit();
    }

    public static String jsonToStringFromAssetFolder(Context context) {
        byte[] data = null;
        try {
            AssetManager manager = context.getAssets();
            InputStream file = manager.open("skills.json");

            data = new byte[file.available()];
            file.read(data);
            file.close();
        } catch (IOException e) {

        }


        return new String(data);
    }

    @Override
    public void saveSkill() {
        getSupportFragmentManager().beginTransaction().replace(R.id.skills_container, new SkillsFragment()).commit();
    }
}

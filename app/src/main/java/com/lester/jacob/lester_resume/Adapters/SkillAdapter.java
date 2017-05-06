package com.lester.jacob.lester_resume.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.lester.jacob.lester_resume.Classes.Skill;
import com.lester.jacob.lester_resume.Classes.SkillDB;
import com.lester.jacob.lester_resume.Fragments.EditSkillFragment;
import com.lester.jacob.lester_resume.R;

import java.util.ArrayList;
import java.util.List;

public class SkillAdapter extends BaseSwipeAdapter {
    private Context mContext;
    private SkillDB db;
    private ArrayList<Skill> skills;

    public SkillAdapter(Context mContext) {
        this.mContext = mContext;
        db = new SkillDB(mContext);
        skills = db.getSkills();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipeLayout;
    }

    @Override
    public View generateView(final int position, final ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.skill_list_item, null);
        SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));

        v.findViewById(R.id.deleteSkillBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deleteCount = db.deleteSkill(((Skill)getItem(position)).getId());
                skills.remove(position);
                notifyDataSetChanged();
                if (deleteCount == 1) {
                    Toast.makeText(mContext, "skill deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "could not delete skill", Toast.LENGTH_SHORT).show();
                }
            }
        });

        v.findViewById(R.id.editSkillBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditSkillFragment newFragment = new EditSkillFragment();
                Bundle args = new Bundle();
                args.putInt(EditSkillFragment.ARG_SKILL, ((Skill)getItem(position)).getId());
                newFragment.setArguments(args);
                final Context context = parent.getContext();
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.skills_container, newFragment)
                        .commit();
                Toast.makeText(mContext, "edit skill", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView t = (TextView)convertView.findViewById(R.id.skillName);
        List<Skill> skills = db.getSkills();
        t.setText(skills.get(position).getName());
    }

    @Override
    public int getCount() {
        return skills.size();
    }

    @Override
    public Object getItem(int position) {
        return skills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

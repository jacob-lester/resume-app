package com.lester.jacob.lester_resume.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lester.jacob.lester_resume.Classes.Skill;
import com.lester.jacob.lester_resume.Classes.SkillDB;
import com.lester.jacob.lester_resume.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditSkillInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditSkillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditSkillFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_SKILL = "param1";

    // TODO: Rename and change types of parameters
    private Skill skill;
    private EditText skillNameEditText;
    private EditText skillDetailEditText;
    private Button updateSkillBtn;
    SkillDB db;

    private EditSkillInteractionListener mListener;

    public EditSkillFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditSkillFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditSkillFragment newInstance(String param1, String param2) {
        EditSkillFragment fragment = new EditSkillFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SKILL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            db = new SkillDB(getContext());
            skill = db.getSkill(getArguments().getInt(ARG_SKILL));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_skill, container, false);
        skillNameEditText = (EditText) view.findViewById(R.id.skill_name_edit_text);
        skillDetailEditText = (EditText) view.findViewById(R.id.skill_detail_edit_text);
        updateSkillBtn = (Button) view.findViewById(R.id.update_skill_btn);

        skillNameEditText.setText(skill.getName());
        skillDetailEditText.setText(skill.getDesc());
        updateSkillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (skillNameEditText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    skill.setName(skillNameEditText.getText().toString());
                }

                if (skillDetailEditText.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    skill.setDesc(skillDetailEditText.getText().toString());
                }

                db.updateSkill(skill);
                Toast.makeText(getContext(), "Skill updated successfully", Toast.LENGTH_LONG).show();
                mListener.updateSkill();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EditSkillInteractionListener) {
            mListener = (EditSkillInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EditSkillInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface EditSkillInteractionListener {
        // TODO: Update argument type and name
        void updateSkill();
    }
}

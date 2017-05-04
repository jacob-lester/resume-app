package com.lester.jacob.lester_resume.Fragments;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.swipe.util.Attributes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lester.jacob.lester_resume.Adapters.SkillAdapter;
import com.lester.jacob.lester_resume.Classes.Skill;
import com.lester.jacob.lester_resume.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SkillsInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SkillsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SkillsInteractionListener mListener;

    public SkillsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SkillsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillsFragment newInstance(String param1, String param2) {
        SkillsFragment fragment = new SkillsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        Gson gson = new Gson();
        String jsonOutput = jsonToStringFromAssetFolder(getContext());
        Type listType = new TypeToken<List<Skill>>(){}.getType();
        List<Skill> skills = (List<Skill>) gson.fromJson(jsonOutput, listType);

        final ListView skillsListView = (ListView) view.findViewById(R.id.skillsListView);

        SkillAdapter skillAdapter = new SkillAdapter(getContext());
        skillsListView.setAdapter(skillAdapter);
        skillAdapter.setMode(Attributes.Mode.Single);

        skillsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onSkillSelected(position);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onSkillSelected(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SkillsInteractionListener) {
            mListener = (SkillsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SkillsInteractionListener");
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
    public interface SkillsInteractionListener {
        // TODO: Update argument type and name
        void onSkillSelected(int position);
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


}

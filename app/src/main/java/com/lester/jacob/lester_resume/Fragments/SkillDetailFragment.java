package com.lester.jacob.lester_resume.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lester.jacob.lester_resume.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SkillDetailInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SkillDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SkillDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_DETAILS = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView skillDetailText;
    // TODO: Rename and change types of parameters
    private String detail;

    private SkillDetailInteractionListener mListener;

    public SkillDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SkillDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SkillDetailFragment newInstance(String param1, String param2) {
        SkillDetailFragment fragment = new SkillDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DETAILS, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            detail = getArguments().getString(ARG_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_skill_detail, container, false);

        skillDetailText = (TextView) view.findViewById(R.id.skillDetailText);
        skillDetailText.setText(detail);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SkillDetailInteractionListener) {
            mListener = (SkillDetailInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SkillDetailInteractionListener");
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
    public interface SkillDetailInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateSkillDetailView(String details) {
        skillDetailText.setText(details);
    }

}

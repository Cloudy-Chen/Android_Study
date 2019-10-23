package com.sdu.chy.chytest.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sdu.chy.chytest.R;

public class Test1Fragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btn1;

    private OnFragmentInteractionListener mListener;

    public Test1Fragment() {
    }


    public static Test1Fragment newInstance(String param1, String param2) {
        Test1Fragment fragment = new Test1Fragment();
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
                             Bundle savedInstanceState) {
        View contentView =  inflater.inflate(R.layout.fragment_test1, container, false);
        btn1 = (Button)contentView.findViewById(R.id.btn_fragment_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed();
            }
        });
        return contentView;
    }

    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction("Hello 我是FragmentTest1");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            //获取Activity，其中Activity必须实现OnFragmentInteractionListener接口
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}

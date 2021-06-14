package com.cbitss.careforu;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class HealthTips extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HealthTips() {

    }

    public static HealthTips newInstance(String param1, String param2) {
        HealthTips fragment = new HealthTips();
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

    WebView w;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Health Tips");
        View v=inflater.inflate(R.layout.fragment_health_tips, container, false);
        w=(WebView)v.findViewById(R.id.w_health);
        w.setWebViewClient(new dyk());
        w.getSettings().getLoadsImagesAutomatically();
        w.getSettings().getJavaScriptEnabled();
        w.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        w.loadUrl("http://www.gkfacts.in/search/label/Health Tips");
        return v;
    }


    public class dyk extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

    public void onBackPressed() {
        Intent i = new Intent(getContext(), Home.class);
        startActivity(i);


    }

}

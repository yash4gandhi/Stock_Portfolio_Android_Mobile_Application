package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.Activity2;

public class tab1 extends Fragment {

    public String ticker;
    public tab1(String ticker ) {
        this.ticker = ticker;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Activity2 st = new Activity2();
//        String input = st.ticker;

        WebView rwebview = view.findViewById(R.id.w1);
        rwebview.getSettings().setJavaScriptEnabled(true);
        rwebview.getSettings().setAllowFileAccess(true);
        rwebview.setWebChromeClient(new WebChromeClient());
        rwebview.loadUrl("file:///android_asset/dailychart.html");
        rwebview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:setTicker('" + ticker + "')");
            }
        });
    }
}
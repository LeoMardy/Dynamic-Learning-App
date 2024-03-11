package com.example.drawerapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.speech.RecognitionService;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;

import com.example.drawerapp.databinding.FragmentItemBinding;


public class Item_Fragment extends Fragment {

    FragmentItemBinding binding;
    public static String WEB_URL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemBinding.inflate(getLayoutInflater(), container, false);

        binding.webViw.getSettings().getJavaScriptEnabled();
        binding.webViw.setWebViewClient(new WebViewClient());
        binding.webViw.loadUrl(WEB_URL);

        /*======================= Manage State of WebVIew =========================*/
        binding.webViw.setOnKeyListener(new View.OnKeyListener() {
           @Override
           public boolean onKey(View view, int i, KeyEvent keyEvent) {
               if (keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                   if (i==KeyEvent.KEYCODE_BACK){
                       if (binding.webViw.canGoBack()){
                           binding.webViw.goBack();
                       }else {
                           getActivity().onBackPressed();
                       }
                   }
               }

               return true;
           }

       });

        return binding.getRoot();
    }
}
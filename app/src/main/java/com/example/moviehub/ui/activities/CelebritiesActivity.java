package com.example.moviehub.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.moviehub.ui.fragments.CastFragment;
import com.example.moviehub.R;
import com.example.moviehub.utils.Type;

public class CelebritiesActivity extends AppCompatActivity {

    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrities);

       String s= getIntent().getStringExtra("id");
        Type.MovieOrTvshow type = (Type.MovieOrTvshow) getIntent().getSerializableExtra("type");
        Type.Credit creditType = (Type.Credit) getIntent().getSerializableExtra("creditType");


        layout=findViewById(R.id.frame);
        setFragment(CastFragment.newInstance(s,type,creditType) );
    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frame, fragment);
        t.commit();
    }
}

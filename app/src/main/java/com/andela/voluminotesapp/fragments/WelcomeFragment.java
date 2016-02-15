package com.andela.voluminotesapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.adapters.WelcomeAdapter;
import com.andela.voluminotesapp.callbacks.WelcomeListener;

/**
 * Created by andela on 2/15/16.
 */
public class WelcomeFragment extends Fragment {

    private WelcomeListener welcomeListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.welcome_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        WelcomeAdapter welcomeAdapter = new WelcomeAdapter(getContext());
        welcomeAdapter.setOnWelcomeListener(this.welcomeListener);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.welcomeRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(welcomeAdapter);
        recyclerView.setHasFixedSize(true);

        super.onViewCreated(view, savedInstanceState);
    }

    public void setWelcomeListener(WelcomeListener welcomeListener){
        this.welcomeListener = welcomeListener;
    }
}
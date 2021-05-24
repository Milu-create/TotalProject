package com.totalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Menu extends Fragment {
    private String val;
    private Button btn_play;
    private View view;

    OnMenuDataListener frListener;

    public Menu() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            val = getArguments().getString(MainActivity.KEY);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnMenuDataListener) {
            frListener = (OnMenuDataListener) context;
        } else {
            throw new NullPointerException("Объект не создан на основе класса Menu");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu, container, false);
        btn_play = view.findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ftrans = getFragmentManager().beginTransaction();
                ftrans.replace(R.id.root, new Login());
                ftrans.commit();

            }
        });
        return view;
    }

    public interface OnMenuDataListener {
        void onMenuDataListener(String str);
    }
}

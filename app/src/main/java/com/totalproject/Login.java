package com.totalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

public class Login extends Fragment {
    public static User user;
    EditText name;
    Button contin;
    private String val;
    private View view;
    Login.OnLoginDataListener frListener;

    public Login() {}

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            val = getArguments().getString(MainActivity.KEY);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Login.OnLoginDataListener) {
            frListener = (Login.OnLoginDataListener) context;
        } else {
            throw new NullPointerException("Объект не создан на основе класса Login");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.log_in, container, false);
        contin = view.findViewById(R.id.cont);
        contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = view.findViewById(R.id.editText_username);
                if(TextUtils.isEmpty(name.getText().toString())) {
                    Snackbar.make(view, "Введите имя!", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                user = new User(name.getText().toString());
                Snackbar.make(view, "Добро пожаловать, "+user.getName()+"! У вас "+user.getPoint()+" монет.", Snackbar.LENGTH_LONG).show();
                FragmentTransaction ftrans = getFragmentManager().beginTransaction();
                ftrans.replace(R.id.root, new Menu_play());
                ftrans.commit();

            }
        });
        return view;
    }

    public interface OnLoginDataListener {
        void onLoginDataListener(String str);
    }
}
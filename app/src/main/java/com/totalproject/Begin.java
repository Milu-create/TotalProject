package com.totalproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Begin extends Fragment{

    private String val;
    private Button btn2;
    private View view;
    private TextView textView;

    Begin.OnBeginDataListener frListener;

    public Begin() {}

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
        if(context instanceof Begin.OnBeginDataListener) {
            frListener = (Begin.OnBeginDataListener) context;
        }
        else {
            throw new NullPointerException("Объект не создан на основе класса Begin");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.begin, container, false);
        btn2 = view.findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ftrans = getFragmentManager().beginTransaction();
                ftrans.replace(R.id.root, new Menu());
                ftrans.commit();
            }
        });
        textView = view.findViewById(R.id.textView);
        DelayedPrinter.Word word = new DelayedPrinter.Word(50, "-Хм... Я уже был здесь.\n-Капитан, мы приземлились в Научном городе, улица Просвещения.\n" +
                "-Какой сейчас год?\n-...Если я не ошибаюсь, 1989.\n-Ого, куда нас занесло. У нас есть время зайти в моё любимое место?\n-Конечно, корабль пока перезагружается." +
                "\n-Отлично! Мы отправляемся в компьютерный клуб Neo Club.");
        word.setOffset(50);
        DelayedPrinter.printText(word, textView);
        return view;
    }

    public interface OnBeginDataListener {
        void onBeginDataListener(String str);
    }
}

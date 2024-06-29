package com.example.unibody.me.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.unibody.R;
import com.example.unibody.login.activity.LoginActivity;

public class MyQuizActivity  extends AppCompatActivity {



    private EditText quiz1_edit;
    private EditText quiz2_edit;
    private Button save_button;
    private ImageView back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        setContentView(R.layout.activity_my_quiz);
        quiz1_edit = findViewById(R.id.me_quiz1_edit);
        quiz2_edit = findViewById(R.id.me_quiz2_edit);
        save_button = findViewById(R.id.me_quiz_save);

        back = findViewById(R.id.my_quiz_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyQuizActivity.this.finish();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(MyQuizActivity.this,"My quiz saved",Toast.LENGTH_SHORT).show();
                MyQuizActivity.this.finish();
            }
        });
    }



    public String quiz1(){
        return quiz1_edit.getText().toString();
    }

    public String quiz2(){
        return quiz2_edit.getText().toString();
    }
}
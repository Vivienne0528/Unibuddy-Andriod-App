package com.example.unibody.finder.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unibody.R;
import com.example.unibody.databinding.ActivityFinderProfileBinding;
import com.example.unibody.finder.fragment.bean.FilterUserBean;
import static com.example.unibody.me.fragment.util.Util.getBitmapFromURL;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FinderProfileActivity extends AppCompatActivity {

    ActivityFinderProfileBinding binding;

    FilterUserBean.FilterUsersBean student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinderProfileBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        student = (FilterUserBean.FilterUsersBean) getIntent().getSerializableExtra("filterUsers");

        binding.finderProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.finderProfilePhoto.setImageBitmap(getBitmapFromURL(student.getAvatar_url()));

        binding.finderProfileDistance.setText(student.getDistance());
        binding.finderProfileName.setText(student.getUsername());
        binding.finderProfileUniversity.setText(student.getUniversity());
        binding.finderProfileStatus.setText(student.getStatus());
        binding.finderProfileMajor.setText(student.getMajor());

        if ("male".equals(student.getGender())) {
            binding.finderProfileGender.setImageResource(R.drawable.male);
        } else {
            binding.finderProfileGender.setImageResource(R.drawable.female);
        }
    }

}
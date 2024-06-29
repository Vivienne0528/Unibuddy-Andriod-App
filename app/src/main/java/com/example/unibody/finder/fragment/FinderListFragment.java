package com.example.unibody.finder.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;
import com.example.unibody.finder.fragment.bean.FilterUserBean;
import com.example.unibody.me.fragment.adapter.MyFriendsAdapter;
import com.example.unibody.me.fragment.bean.FriendBean;
import com.example.unibody.me.fragment.http.ApiBuilder;
import com.example.unibody.me.fragment.http.ApiClient;
import com.example.unibody.me.fragment.http.CallBack;
import com.example.unibody.me.fragment.util.Util;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 在哪个类
 *
 */

public class FinderListFragment extends Fragment implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView TopNavigationView;
    RecyclerView studentList;
    FinderProfileFragment profileFragment;
    private StudentListAdapter adapter;
    private List<FilterUserBean.FilterUsersBean> list = new ArrayList<>();
    private List<FilterUserBean.FilterUsersBean> students_filter = new ArrayList<>();
    public static FinderListFragment newInstance(String gender, String status, String age, String university, String distance) {
        FinderListFragment finder = new FinderListFragment();
        Bundle args = new Bundle();
        args.putString("gender", gender);
        args.putString("age", age);
        args.putString("status", status);
        args.putString("university", university);
        args.putString("distance", distance);
        finder.setArguments(args);
        return finder;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.finder_list_fragment, container, false);

        getActivity().findViewById(R.id.bottom_navigator).setVisibility(View.VISIBLE);
        Button map_view = view.findViewById(R.id.map_view);
        map_view.setOnClickListener(this);


        studentList = view.findViewById(R.id.list_info);
        studentList.setLayoutManager(new LinearLayoutManager(getActivity()));



        adapter = new StudentListAdapter(getActivity(), students_filter, new StudentListAdapter.OnItemClickListaner() {
            @Override
            public void onItemCLicked(int position) {

                Intent intent = new Intent(getActivity(), FinderProfileActivity.class);
                intent.putExtra("filterUsers", students_filter.get(position));
                startActivity(intent);

            }
        });
        studentList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        studentList.setAdapter(adapter);
        TopNavigationView = view.findViewById(R.id.finder_top_navigator);
        TopNavigationView.setOnNavigationItemSelectedListener(this);
        loadData();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.map_view:
                getFragmentManager().beginTransaction().replace(R.id.fragment, new FinderFragment()).commit();
        }
    }

    private void loadData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "test1");
        map.put("status", "");
        map.put("gender", "");
        map.put("age", "");
        map.put("university", "");
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/user/getUserByFilter").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {

                FilterUserBean bean = new Gson().fromJson(data.toString(),FilterUserBean.class);

                List<FilterUserBean.FilterUsersBean> filterUsers = bean.getFilterUsers();
                list.clear();
                list.addAll(filterUsers);

                for (int i = 0; i < list.size(); i++)
                {
                    if (list.get(i).getGender() == null) {
                        list.get(i).setGender("male");
                    }
                    if (list.get(i).getStatus() == null) {
                        list.get(i).setStatus("single");
                    }
                    if (Filter.GENDER.equals("")&&Filter.STATUS.equals("")){
                        students_filter.add(list.get(i));
                    }else if(list.get(i).getGender().equalsIgnoreCase(Filter.GENDER)&&Filter.STATUS.equals("")){
                        students_filter.add(list.get(i));
                    }else if(list.get(i).getStatus().equalsIgnoreCase(Filter.STATUS)&&Filter.GENDER.equals("")){
                        students_filter.add(list.get(i));
                    }else if(list.get(i).getGender().equalsIgnoreCase(Filter.GENDER)
                            &&list.get(i).getStatus().equalsIgnoreCase(Filter.STATUS)){
                        students_filter.add(list.get(i));
                    }
                }

                adapter.notifyDataSetChanged();


                Log.e("AAAA", "onResponse: " + data);
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }

//    public void getDataFromServer() {
//        // 自动生成一批数据
//        List<Student> students = new ArrayList<>();
//        String url = "http://3.26.21.18/api/v1/user/getUserByFilter";
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("gender","");
//            jsonObject.put("status","");
//            jsonObject.put("age","");
//            jsonObject.put("university","university");
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//        //创建一个OkHttpClient对象
//        OkHttpClient okHttpClient = new OkHttpClient();
//        MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/json; charset=utf-8");
//        //创建一个请求对象
//        Request request = new Request.Builder()
//                .url(url)
//                .post(RequestBody.create(MEDIA_TYPE_TEXT, jsonObject.toString()))
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("XXXXXXXXXXXXXXXX", "onFailure" + e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                Log.i("YYYYYYYYYYYYYYYYY", "onResponse: " + result);
//                try {
//                    JSONArray jsonArray = new JSONArray(result);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        students.add(new Student(jsonObject.getString("username"),
//                                "game",
//                                jsonObject.getString("gender"),
//                                jsonObject.getString("university"),
//                                jsonObject.getString("status"),
//                                "",
//                                R.mipmap.student1,
//                                jsonObject.getDouble("lat"),
//                                jsonObject.getDouble("lon")));
//                    }
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            StudentListAdapter studentListAdapter = new StudentListAdapter(getActivity(), students, new StudentListAdapter.OnItemClickListaner() {
//                                @Override
//                                public void onItemCLicked(int position) {
//
//                                    Intent intent = new Intent(getActivity(), FinderProfileActivity.class);
//                                    intent.putExtra("student", students.get(position));
//                                    startActivity(intent);
//
//                                }
//                            });
//                            studentList.setAdapter(studentListAdapter);
//                        }
//                    });
//                } catch (JSONException e) {
//                }
//            }
//        });
//
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.student_nav:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FinderCampusFragment()).commit();
                return true;
        }
        return false;
    }
}
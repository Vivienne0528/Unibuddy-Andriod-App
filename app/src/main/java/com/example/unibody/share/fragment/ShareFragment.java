package com.example.unibody.share.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unibody.R;
import com.example.unibody.me.fragment.PhotoActivity;
import com.example.unibody.me.fragment.adapter.MomentsAdapter;
import com.example.unibody.me.fragment.bean.MomentBean;
import com.example.unibody.me.fragment.http.ApiBuilder;
import com.example.unibody.me.fragment.http.ApiClient;
import com.example.unibody.me.fragment.http.CallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ShareFragment extends Fragment {

    private RecyclerView rvShare;
    private MomentsAdapter adapter;
    private List<MomentBean.MomentsBean> list = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.share_fragment, container, false);
        rvShare = view.findViewById(R.id.rv_share);

        rvShare.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        adapter = new MomentsAdapter(getActivity(), list, new MomentsAdapter.CallBack() {

            @Override
            public void delMoment(String id) {
                deleteMoment(id);
            }

            @Override
            public void showBigPhoto(String path) {
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.putExtra("path", path);
                intent.putExtra("canDel", "");
                startActivity(intent);
            }
        });
        rvShare.setAdapter(adapter);
        loadData();

        return view;
    }


    private void deleteMoment(String id) {
        HashMap<String, String> map = new HashMap<>();
        String str;
        map.put("id", id);
        JSONObject object = new JSONObject(map);
        str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/moment/deleteMoment").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doPost(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                Log.e("AAAA", "onResponse: " + data.toString());
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "delete moment success", Toast.LENGTH_SHORT).show();
                }
                loadData();
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "delete moment fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadData() {
        HashMap<String, String> map = new HashMap<>();
        JSONObject object = new JSONObject(map);
        String str = object.toString();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
        ApiBuilder builder = new ApiBuilder().Url("api/v1/moment").Headers("Content-Type", "application/json").Body(body);

        ApiClient.getInstance().doGet(builder, new CallBack<Object>() {
            @Override
            public void onResponse(Object data) {
                List<MomentBean.MomentsBean> moments = new ArrayList<MomentBean.MomentsBean>();

                Type type = new TypeToken<ArrayList<MomentBean.MomentsBean>>() {
                }.getType();

                moments = new Gson().fromJson(data.toString(), type);
                list.clear();
                list.addAll(moments);
                adapter.notifyDataSetChanged();


                Log.e("AAAA", "onResponse: " + data);
            }


            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }
}

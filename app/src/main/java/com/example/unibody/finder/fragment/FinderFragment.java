package com.example.unibody.finder.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.unibody.finder.fragment.bean.FilterUserBean;
import com.example.unibody.me.fragment.http.ApiBuilder;
import com.example.unibody.me.fragment.http.ApiClient;
import com.example.unibody.me.fragment.http.CallBack;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.example.unibody.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FinderFragment extends Fragment {

    private List<FilterUserBean.FilterUsersBean> list = new ArrayList<>();
    private List<FilterUserBean.FilterUsersBean> students_filter = new ArrayList<>();
    // private StudentListAdapter adapter;
    BottomNavigationView TopNavigationView;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Location UserLocation;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finder, container, false);



        getActivity().findViewById(R.id.bottom_navigator).setVisibility(View.VISIBLE);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        TopNavigationView = view.findViewById(R.id.finder_top_navigator);

        TopNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.campus_nav:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new FinderCampusFragment()).commit();
                        return true;
                }
                return false;
            }
        });

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();

        }
        else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
        }

        Button list_view = view.findViewById(R.id.list_view);
        list_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.list_view) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment, new FinderListFragment()).commit();
                }
            }
        });

        ImageButton filter = view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.filter) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment, new FinderFilterFragment()).commit();
                }
            }
        });
       // loadData();
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        }
        else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {

        LocationManager locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task)
                {
                    UserLocation = task.getResult();

                    if (UserLocation != null) {
                        setUserMarker(UserLocation);
                    }
                    else {
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(1000).setFastestInterval(1000).setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult)
                            {
                                UserLocation = locationResult.getLastLocation();
                                setUserMarker(UserLocation);
                            }
                        };

                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                    }
                }
            });
        }
        else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void setUserMarker (Location location){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.clear();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions options = new MarkerOptions().position(latLng).title("Me");
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(options);
                loadData(googleMap);
//                for (int i = 0; i < students.size(); i++)
//                {
//                    if (Filter.GENDER.equals("")&&Filter.STATUS.equals("")){
//                        createMarker(googleMap, students.get(i));
//                    }else if (students.get(i).getSex().equalsIgnoreCase(Filter.GENDER)&&Filter.STATUS.equals("")){
//                        createMarker(googleMap, students.get(i));
//                    }else if (students.get(i).getSex().equalsIgnoreCase(Filter.STATUS)&&Filter.GENDER.equals("")){
//                        createMarker(googleMap, students.get(i));
//                    }else if(students.get(i).getSex().equalsIgnoreCase(Filter.GENDER)
//                            && students.get(i).getStatus().equalsIgnoreCase(Filter.STATUS)){
//                        createMarker(googleMap, students.get(i));
//                    }
//                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        String name = marker.getTitle();
                        for (int i = 0; i < students_filter.size(); i++)
                        {
                            if (students_filter.get(i).getUsername().equals(name))
                            {
                                Intent intent = new Intent(getActivity(), FinderProfileActivity.class);
                                intent.putExtra("filterUsers", students_filter.get(i));
                                startActivity(intent);
                                return true;
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void createMarker(GoogleMap googleMap, FilterUserBean.FilterUsersBean student){
        float color;
        if (student.getGender().equalsIgnoreCase( "male"))
        {
            color = BitmapDescriptorFactory.HUE_BLUE;
        }
        else
        {
            color = BitmapDescriptorFactory.HUE_ROSE;
        }
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(student.getLoc().get(0), student.getLoc().get(1)))
                .title(student.getUsername())
                .icon(BitmapDescriptorFactory.defaultMarker(color)));
    }


    private void loadData(@NonNull GoogleMap googleMap) {
        HashMap<String, String> map = new HashMap<>();
        //map.put("username", Util.user);
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
//                    if (Filter.GENDER.equals("")&&Filter.STATUS.equals("")){
//                        students_filter.add(list.get(i));
//                        createMarker(googleMap, students_filter.get(i));
//                    }else if(list.get(i).getGender().equalsIgnoreCase(Filter.GENDER)&&Filter.STATUS.equals("")){
//                        students_filter.add(list.get(i));
//                        createMarker(googleMap, students_filter.get(i));
//                    }else if(list.get(i).getStatus().equalsIgnoreCase(Filter.STATUS)&&Filter.GENDER.equals("")){
//                        students_filter.add(list.get(i));
//                    }else if(list.get(i).getGender().equalsIgnoreCase(Filter.GENDER)
//                            &&list.get(i).getStatus().equalsIgnoreCase(Filter.STATUS)){
//                        students_filter.add(list.get(i));
//                    }


                    if (Filter.GENDER.equals("")&&Filter.STATUS.equals("")){
                        students_filter.add(list.get(i));
                        createMarker(googleMap, students_filter.get(i));
                    }else if (students_filter.get(i).getGender().equalsIgnoreCase(Filter.GENDER)&&Filter.STATUS.equals("")){
                        students_filter.add(list.get(i));
                        createMarker(googleMap, students_filter.get(i));
                    }else if (students_filter.get(i).getGender().equalsIgnoreCase(Filter.STATUS)&&Filter.GENDER.equals("")){
                        students_filter.add(list.get(i));
                        createMarker(googleMap, students_filter.get(i));
                    }else if(students_filter.get(i).getGender().equalsIgnoreCase(Filter.GENDER)
                            && students_filter.get(i).getStatus().equalsIgnoreCase(Filter.STATUS)){
                        students_filter.add(list.get(i));
                        createMarker(googleMap, students_filter.get(i));
                    }
                }

                // adapter.notifyDataSetChanged();


                Log.e("AAAA", "onResponse: " + data);
            }

            @Override
            public void onFail(String msg) {
                Log.e("AAAA", "onFail: " + msg);
            }
        });
    }



}
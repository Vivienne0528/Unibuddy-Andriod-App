package com.example.unibody.finder.fragment;

import static com.example.unibody.me.fragment.util.Util.getBitmapFromURL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.unibody.R;
import com.example.unibody.databinding.FinderListStudentListBinding;
import com.example.unibody.finder.fragment.bean.FilterUserBean;
import com.example.unibody.me.fragment.view.CircleImageView;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> {
    FinderListStudentListBinding finderListStudentListBinding;

    private Context context;
    private List<FilterUserBean.FilterUsersBean> examBeens;


    private OnItemClickListaner onItemClickListaner;

    public StudentListAdapter(Context context, List<FilterUserBean.FilterUsersBean> examBeens, OnItemClickListaner onItemClickListaner) {
        this.context = context;
        this.examBeens = examBeens;
        this.onItemClickListaner = onItemClickListaner;
    }

    public interface OnItemClickListaner {
        void onItemCLicked(int position);
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        finderListStudentListBinding = FinderListStudentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        MyViewHolder holder = new MyViewHolder(finderListStudentListBinding);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.finderListStudentListBinding.finderListContentDistance.setText(examBeens.get(position).getDistance());
        holder.finderListStudentListBinding.finderListContentName.setText(examBeens.get(position).getUsername());
        Log.e("AAAA", "AVATAR_URL: " +examBeens.get(position).getAvatar_url());

        holder.finderListStudentListBinding.finderListContentUniversity.setText(examBeens.get(position).getUniversity());
        holder.finderListStudentListBinding.finderListContentStatus.setText(examBeens.get(position).getStatus());

        holder.finderListStudentListBinding.finderListContentPhoto.setImageBitmap(getBitmapFromURL(examBeens.get(position).getAvatar_url()));
        //Glide.with(context).load(examBeens.get(position).getAvatar_url()).apply(new RequestOptions().placeholder(holder.headImg.getDrawable())).into(holder.headImg);

        if (examBeens.get(position).getStatus().equalsIgnoreCase("dating")) {
            holder.finderListStudentListBinding.finderListContentStatusIcon.setImageResource(R.drawable.dating1);
        } else if (examBeens.get(position).getStatus().equalsIgnoreCase( "single")) {
            holder.finderListStudentListBinding.finderListContentStatusIcon.setImageResource(R.drawable.single1);
        } else {
            holder.finderListStudentListBinding.finderListContentStatusIcon.setImageResource(R.drawable.secret);
        }

        if   ( examBeens.get(position).getGender() == null  || examBeens.get(position).getGender().equalsIgnoreCase("male")) {
            holder.finderListStudentListBinding.finderListContentGender.setImageResource(R.drawable.man);

        } else {
            holder.finderListStudentListBinding.finderListContentGender.setImageResource(R.drawable.woman);
        }

        holder.finderListStudentListBinding.finderListContentDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListaner.onItemCLicked(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return examBeens.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView headImg;
        private FinderListStudentListBinding finderListStudentListBinding;

        public MyViewHolder(FinderListStudentListBinding finderListStudentListBinding) {
            super(finderListStudentListBinding.getRoot());
            //headImg = itemView.findViewById(R.id.finder_list_content_photo);
            this.finderListStudentListBinding = finderListStudentListBinding;
           // headImg = this.finderListStudentListBinding.findViewById(R.id.finder_list_content_photo);
        }
    }

}

package com.example.collegebuddyversion2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddyversion2.Models.Members;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.Utils.retrofitInstance;

import java.util.ArrayList;
import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.memberHolder>{

    private ArrayList<Members> membersList;
    private Context context;
    private retrofitInstance instance = new retrofitInstance();


    public MembersAdapter(ArrayList<Members> membersList , Context context) {
        this.membersList = membersList;
        this.context = context;
    }

    @NonNull
    @Override
    public MembersAdapter.memberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.members_layout,
                parent, false);
        return new memberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembersAdapter.memberHolder holder, int position) {

        Members student = membersList.get(position);

        holder.setMember_name_text_view(student.getMember_name());
        holder.setMember_branch_text_view(student.getMember_branch());
        holder.setMember_year_text_view(student.getMember_year());

    }

    @Override
    public int getItemCount() {
        if(membersList!=null) {
            return membersList.size();
        }
        return 0;
    }

    public class memberHolder extends RecyclerView.ViewHolder{

        TextView member_name_text_view;
        TextView member_year_text_view;
        TextView member_branch_text_view;


        public memberHolder(@NonNull View itemView) {
            super(itemView);

            member_name_text_view = itemView.findViewById(R.id.user_name_member_layout);
            member_branch_text_view = itemView.findViewById(R.id.branch_text_view_members_list);
            member_year_text_view = itemView.findViewById(R.id.year_text_view_members_list);

        }

        void setMember_name_text_view(String member_name){
            member_name_text_view.setText(member_name);
        }

        void setMember_branch_text_view(String member_branch){
            member_branch_text_view.setText(member_branch);
        }

        void setMember_year_text_view(String member_year){
            member_year_text_view.setText(member_year);
        }

    }

}

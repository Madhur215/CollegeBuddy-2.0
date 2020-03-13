package com.example.collegebuddyversion2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collegebuddyversion2.Models.Subjects;
import com.example.collegebuddyversion2.R;

import java.util.ArrayList;
import java.util.List;

public class SubjectsAdapter extends BaseAdapter {

    private Context ctx;
    private List<Subjects> subjectsArrayList;
    private LayoutInflater inflater;

    public SubjectsAdapter(Context ctx , List<Subjects> subjectsArrayList) {
        this.ctx = ctx;
        this.subjectsArrayList = subjectsArrayList;
        inflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        try{
            return subjectsArrayList.size();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView names_tv;
        Subjects sub = subjectsArrayList.get(position);
        convertView = inflater.inflate(R.layout.subjects_layout , null);
        names_tv = convertView.findViewById(R.id.names_text_view);
        names_tv.setText(sub.getSubject());

        // SET SUBJECT IMAGE !

        return convertView;

    }
}

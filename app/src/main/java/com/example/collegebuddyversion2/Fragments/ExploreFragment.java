package com.example.collegebuddyversion2.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddyversion2.Adapter.MembersAdapter;
import com.example.collegebuddyversion2.Models.Members;
import com.example.collegebuddyversion2.R;
import com.example.collegebuddyversion2.ViewModels.ExploreViewModel;

import java.util.ArrayList;

public class ExploreFragment extends Fragment {

    private ArrayList<Members> membersList = new ArrayList<>();
    private RecyclerView membersRecyclerView;
    private MembersAdapter membersAdapter;

    private static String LIST_STATE = "list_state";
    private Parcelable savedRecyclerLayoutState;
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.explore_fragment, container, false);
        membersRecyclerView = view.findViewById(R.id.members_recycler_view);
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        membersRecyclerView.setHasFixedSize(true);

        ExploreViewModel exploreViewModel = new ViewModelProvider(ExploreFragment.this)
                .get(ExploreViewModel.class);
        membersList = exploreViewModel.getMembers();
        membersAdapter = new MembersAdapter(membersList, getContext());
        membersRecyclerView.setAdapter(membersAdapter);


        return view;
    }
}

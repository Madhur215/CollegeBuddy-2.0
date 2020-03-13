package com.example.collegebuddyversion2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddyversion2.Models.SubjectPdfList;
import com.example.collegebuddyversion2.R;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryHolder> {

    private List<SubjectPdfList> subjectPdfLists;
    private OnNotesClickListener listener;

    public void setNotes(List<SubjectPdfList> subjectPdfLists){
        this.subjectPdfLists = subjectPdfLists;
        notifyDataSetChanged();
    }

    public interface OnNotesClickListener{
        void onNotesClick(int position);
//        void deleteNotes(int position);
    }

    public void setOnNotesClickListener(OnNotesClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public LibraryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_items_layout,
                parent, false);
        return new LibraryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryHolder holder, int position) {
        SubjectPdfList notes = subjectPdfLists.get(position);
        holder.note_name_text_view.setText(notes.getPdf_name());

    }

    @Override
    public int getItemCount() {
        try{
            return subjectPdfLists.size();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }

    public class LibraryHolder extends RecyclerView.ViewHolder{

        TextView note_name_text_view;
        TextView note_user_name_text_view;
        TextView number_of_pages_text_view;

        public LibraryHolder(@NonNull View itemView) {
            super(itemView);
            note_name_text_view = itemView.findViewById(R.id.note_name_text_view_home);
            note_user_name_text_view = itemView.findViewById(R.id.notes_user_name_text_view);
            number_of_pages_text_view = itemView.findViewById(R.id.number_of_pages_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onNotesClick(position);
                    }
                }
            });

        }
    }

}

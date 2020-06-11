package com.example.todoapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;

public class HorizontalTagAdapter extends RecyclerView.Adapter<HorizontalTagAdapter.HorizontalViewHolder> {

    private String[] tagTitles;

    public HorizontalTagAdapter(String[] tagTitles){
        this.tagTitles = tagTitles;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_tag, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        holder.tagTitle.setText(tagTitles[position]);
    }

    @Override
    public int getItemCount() {
        return tagTitles.length;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        TextView tagTitle;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            tagTitle = itemView.findViewById(R.id.tag_title_textView);
        }
    }
}

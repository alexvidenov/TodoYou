package com.example.todoapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.modules.Tag;

public class HorizontalTagAdapter extends RecyclerView.Adapter<HorizontalTagAdapter.HorizontalViewHolder> {

    private Tag[] tags;

    public HorizontalTagAdapter(Tag[] tags){
        this.tags = tags;
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
        holder.tagTitle.setText(tags[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return tags.length;
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        TextView tagTitle;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            tagTitle = itemView.findViewById(R.id.tag_title_textView);
        }
    }
}

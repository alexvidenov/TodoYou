package com.example.todoapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.modules.Tag;

import java.util.List;
import java.util.Map;

// TODO: TBA Use
public class GridTagAdapter extends SimpleAdapter {

    private Tag[] tags;

    public GridTagAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public GridTagAdapter(Tag[] tags,
                          Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.tags = tags;
    }

    @Override
    public int getCount() {
        return tags.length;
    }

    @Override
    public Object getItem(int position) {
        return tags[position];
    }

    @Override
    public long getItemId(int position) {
        return tags[position].getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null; // TODO: Implement with stuff on what to do from other method here
    }

    /* @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.todo_tag, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        holder.tagTitle.setText(tags[position].getTitle());
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder{
        TextView tagTitle;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            tagTitle = itemView.findViewById(R.id.tag_title);
        }
    } */
}

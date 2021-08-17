package com.example.taskmaster;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskkHolder> {

    private List<Task> tasksList;

    public TaskAdapter(List<Task> taskList) {
        this.tasksList=taskList;

    }

    @NonNull
    @Override
    public TaskkHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);

        return new TaskkHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull  TaskAdapter.TaskkHolder holder, int position) {
        Task task= tasksList.get(position);
        holder.title.setText(task.getTitle());
        holder.body.setText(task.getBody());
        holder.state.setText(task.getState());
        holder.id=position;

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    static class TaskkHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView body;
        private TextView state;
        private Button detail;
        private int id;

        public TaskkHolder(@NonNull  View itemView ) {

            super(itemView);
            title= itemView.findViewById(R.id.taskTitle);
            body= itemView.findViewById(R.id.body);
            state= itemView.findViewById(R.id.statue);
            detail= itemView.findViewById(R.id.Detail);

//            detail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDetail( id);
//
//                }
//            });
        }



    }




}

package com.example.taskmaster;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Todo;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskkHolder> {

    private List<Todo> tasksList;
    private onTaskClickListener listener;

    public interface onTaskClickListener{

        void onItemClick (int position);
        void onDeleteItem (int position);
    }

    public TaskAdapter(List<Todo> taskList, onTaskClickListener listener) {
        this.tasksList=taskList;
        this.listener=listener;

    }

    @NonNull
    @Override
    public TaskkHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);

        return new TaskkHolder(view,listener) ;
    }

    @Override
    public void onBindViewHolder(@NonNull  TaskAdapter.TaskkHolder holder, int position) {
        Todo task= tasksList.get(position);
        holder.title.setText(task.getTitle());
//        Log.i("adapter","the item title:"+task.getTitle());

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
        private TextView link;
        private Button delete;
        private int id;
        private onTaskClickListener listener;

        private final View.OnClickListener cardListener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(getLayoutPosition());
            }
        };
        private final View.OnClickListener deleteListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteItem(getAdapterPosition());
            }
        };
        public TaskkHolder(@NonNull  View itemView, onTaskClickListener listener ) {

            super(itemView);

            this.listener=listener;
            title= itemView.findViewById(R.id.taskTitle);
            body= itemView.findViewById(R.id.body);
            state= itemView.findViewById(R.id.statue);
            delete= itemView.findViewById(R.id.Detail);


            delete.setOnClickListener(deleteListener);
            itemView.setOnClickListener(cardListener);

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

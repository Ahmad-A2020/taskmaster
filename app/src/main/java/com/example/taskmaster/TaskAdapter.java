package com.example.taskmaster;


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

    private final List<Todo> tasksList;
    private final onTaskClickListener listener;

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

    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    static class TaskkHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final TextView body;
        private final TextView state;

        public TaskkHolder(@NonNull  View itemView, onTaskClickListener listener ) {

            super(itemView);

            title= itemView.findViewById(R.id.taskTitle);
            body= itemView.findViewById(R.id.body);
            state= itemView.findViewById(R.id.statue);
            Button delete = itemView.findViewById(R.id.Detail);


            View.OnClickListener deleteListener = view -> listener.onDeleteItem(getAdapterPosition());
            delete.setOnClickListener(deleteListener);
            View.OnClickListener cardListener = v -> listener.onItemClick(getLayoutPosition());
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

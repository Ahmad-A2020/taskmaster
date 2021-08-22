package com.example.taskmaster;

import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TasksDatamanager {
    private static TasksDatamanager instance= null;
    private List<Todo> tasks = new ArrayList<>();


    private TasksDatamanager(){

    }
    public static TasksDatamanager getInstance(){
        if (instance== null){
            instance = new TasksDatamanager();
        }
        return instance;
    }

    public List<Todo> getTasks() {
        return tasks;
    }

    public void setTasks(List<Todo> tasks) {
        this.tasks = tasks;
    }
}

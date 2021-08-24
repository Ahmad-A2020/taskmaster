package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // download image
        ImageView image= (ImageView) findViewById(R.id.imageView4);
        if (getIntent().getStringExtra("linkFile") != null){

            downloadFile(getIntent().getStringExtra("linkFile"),image);
        }

        String TextTitle= getIntent().getStringExtra("taskTitle");
        TextView titleholder = findViewById(R.id.textView5);
        titleholder.setText(TextTitle);


        String TextDesci= getIntent().getStringExtra("taskDescription");
        TextView descriptionholder = findViewById(R.id.textView10);
        descriptionholder.setText(TextDesci);

        String TextState= getIntent().getStringExtra("taskstate");
        TextView stateholder = findViewById(R.id.textView9);
        stateholder.setText(TextState);
        image.setImageResource(R.drawable.download);



//        findViewById(R.id.imageView4).setImage



    }

    private void downloadFile(String Key, ImageView image){
        File imageFile;
        Amplify.Storage.downloadFile(
                Key,
                imageFile= new File(getApplicationContext().getFilesDir() + "/download.png"),
                result ->{
                    Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

                    image.setImageBitmap(myBitmap);

                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());
                } ,
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );

    }
}
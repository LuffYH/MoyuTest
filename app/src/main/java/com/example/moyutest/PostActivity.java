package com.example.moyutest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.moyutest.util.BaseActivity;

public class PostActivity extends BaseActivity {

    ImageView IV_idea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        IV_idea = (ImageView) findViewById(R.id.compose_idea);
        IV_idea.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.bignerdranch.android.wintervacationhomework;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MoodActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView1;
    TextView textView2;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        ImageView image1 = findViewById(R.id.leaf_one);
        ImageView image2 = findViewById(R.id.leaf_two);
        Animation loadAnimation = AnimationUtils.loadAnimation(this,R.anim.rotate);
        image1.startAnimation(loadAnimation);
        image2.startAnimation(loadAnimation);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText = findViewById(R.id.edit_view);
                String str;
                str = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("data_return",str);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        imageView = findViewById(R.id.image);
        textView1 = findViewById(R.id.another);
        textView2 = findViewById(R.id.another_other);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "famous.ttf");
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);

        editText = findViewById(R.id.edit_view);
        String str;
        str = getIntent().getStringExtra("string_text");
        if(str != null) {
            editText.setText(str);
        }
        editText.setSelection(editText.getText().length());

        Random random = new Random();
        int i = random.nextInt()%5+1;

        switch (i){
            case 1:
                imageView.setImageResource(R.drawable.background1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.background2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.background3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.background4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.background5);
                break;
        }

    }

}

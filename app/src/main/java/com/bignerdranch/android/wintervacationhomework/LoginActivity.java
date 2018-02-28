package com.bignerdranch.android.wintervacationhomework;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edit_one;
    EditText edit_two;
    Button button;
    TextView tx;
    ImageView image;
    String str1;
    String str2;
    private MyDatabaseHelper dbHelper;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new MyDatabaseHelper(this,"PersonalMessage",null,1);

        edit_one = findViewById(R.id.edit_one);
        edit_two = findViewById(R.id.edit_two);
        button = findViewById(R.id.button_one);
        tx = findViewById(R.id.tx);
        image = findViewById(R.id.ball);

        button.setOnClickListener(this);
        tx.setOnClickListener(this);

        Animation loadAnimation = AnimationUtils.loadAnimation(this,R.anim.complex);
        image.startAnimation(loadAnimation);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tx:
                Intent intent1 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_one:
                str1 = edit_one.getText().toString();
                str2 = edit_two.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(db != null){
                if(str1.trim().length() != 0 && str2.trim().length() != 0) {
                    Cursor cursor = db.query("Message", null, null, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        do{
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            String password = cursor.getString(cursor.getColumnIndex("password"));
                            if (str1.equals(name) && str2.equals(password)) {
                                flag = true;
                                Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent2);
                            }
                        }while (cursor.moveToNext());
                        if (!flag) {
                            Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cursor.close();
                    db.close();
                }
                else {
                    Toast.makeText(LoginActivity.this,"请把信息填完整",Toast.LENGTH_SHORT).show();
                  }
                }
                else {
                    Toast.makeText(LoginActivity.this,"请先注册",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

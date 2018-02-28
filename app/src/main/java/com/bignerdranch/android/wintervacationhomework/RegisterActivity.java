package com.bignerdranch.android.wintervacationhomework;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView image;
    EditText edit_one;
    EditText edit_two;
    EditText edit_three;
    Spinner spinner;
    Button button;
    String str1;
    String str2;
    String str3;
    String str4;
    private MyDatabaseHelper dbHelper;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        image = findViewById(R.id.arrow);
        edit_one = findViewById(R.id.edit_one);
        edit_two = findViewById(R.id.edit_two);
        edit_three = findViewById(R.id.edit_three);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.button_two);

        dbHelper = new MyDatabaseHelper(this,"PersonalMessage",null,1);

        image.setOnClickListener(this);
        button.setOnClickListener(this);


        //获取spinner选中item的文本
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               str4 = getResources().getStringArray(R.array.sex)[position];
               parent.setVisibility(View.VISIBLE);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow:
                finish();
                break;
            case R.id.button_two:
                str1 = edit_one.getText().toString();
                str2 = edit_two.getText().toString();
                str3 = edit_three.getText().toString();

                    if(str1.trim().length() == 0 || str2.trim().length() == 0 || str3.trim().length() ==0 ) {
                        Toast.makeText(RegisterActivity.this,"请把信息填完整",Toast.LENGTH_SHORT).show();
                    }
                    else if (!str2.equals(str3)){
                        Toast.makeText(RegisterActivity.this,"两次输入的密码不相等，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                            Cursor cursor = db.query("Message", null, null, null, null, null, null);
                            if (cursor.moveToFirst()) {
                                do {
                                    String name = cursor.getString(cursor.getColumnIndex("name"));
                                    if (str1.equals(name)) {
                                        flag = false;
                                    }
                                } while (cursor.moveToNext());
                            }
                            if(!flag){
                                Toast.makeText(RegisterActivity.this,"用户名已经存在，请重新输入",Toast.LENGTH_SHORT).show();
                            }else {
                                ContentValues values = new ContentValues();
                                values.put("name", str1);
                                values.put("password", str2);
                                values.put("sex", str4);
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                values.clear();
                                db.close();
                                finish();
                            }
                            cursor.close();
                    }
                break;
        }
    }
}

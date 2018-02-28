package com.bignerdranch.android.wintervacationhomework;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton moon;
    RadioButton sun;
    RadioButton star;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    DrawerLayout drawerLayout;
    ImageView iv;

    Toolbar toolbar;

    private Uri uri;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        NavigationView navView = findViewById(R.id.nav_view);
        View header = navView.getHeaderView(0);
        iv=header.findViewById(R.id.header_icon);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.picture2);
        iv.setImageDrawable(new CircleDrawable(bitmap));

        iv.setOnClickListener(this);

        toolbar= findViewById(R.id.toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        //设置滑动菜单项的点击事件
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        moon=findViewById(R.id.moon);
        sun=findViewById(R.id.sun);
        star=findViewById(R.id.star);

        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        transaction.add(R.id.layout,new FirstPage());
        transaction.commit();

        moon.setOnClickListener(this);
        sun.setOnClickListener(this);
        star.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        transaction=manager.beginTransaction();

        switch (v.getId()){
            case R.id.moon:
                transaction.replace(R.id.layout,new FirstPage());
                break;
            case R.id.sun:
                transaction.replace(R.id.layout,new SecondPage());
                break;
            case R.id.star:
                transaction.replace(R.id.layout, new ThirdPage());
                break;

            case R.id.header_icon:
                final String []items;
                items = new String[]{"拍照","相册"};
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("选择上传方式").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
                                try{
                                    if(outputImage.exists()){
                                        outputImage.delete();
                                    }
                                    outputImage.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if(Build.VERSION.SDK_INT >= 24){
                                    uri = FileProvider.getUriForFile(MainActivity.this,"com.bignerdranch.android.wintervacationhomework.fileprovider",outputImage);
                                }else {
                                    uri = Uri.fromFile(outputImage);
                                }
                                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                                startActivityForResult(intent,TAKE_PHOTO);
                                break;
                            case 1:
                                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                                }else {
                                    openAlbum();
                                }
                                break;
                        }
                    }
                }).create();
                dialog.show();
                break;
            
        }
        transaction.commit();
    }

    private void openAlbum(){
        /*Intent intent = new Intent("android.intent.action.GET_CONTIENT");*/
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch (requestCode){
            case TAKE_PHOTO:
                if ((resultCode == RESULT_OK)){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        iv.setImageDrawable(new CircleDrawable(bitmap));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
                default:
                    break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://dowmloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri =data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv.setImageDrawable(new CircleDrawable(bitmap));
        }else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

}

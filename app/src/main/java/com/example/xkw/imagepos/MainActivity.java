package com.example.xkw.imagepos;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_IMG = 1;
    private static final int PERMISSION_REQUEST = 2;

    private ImageView imageView;
    private TextView tv_result;

    private float longitude;
    private float latitude;
    private String uri_str;
    private String location_str;

    private MapBean mapBean;
    private DataBaseUtil dbHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestPermission();

        Button bt_select = (Button)findViewById(R.id.bt_select);
        bt_select.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                     MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,"image/*");
            startActivityForResult(intent, SELECT_IMG);
        });

        tv_result = (TextView)findViewById(R.id.tv_result);
        tv_result.setMovementMethod(new ScrollingMovementMethod());

        imageView = (ImageView)findViewById(R.id.imgv_show);
        imageView.setImageResource(R.drawable.cat);

        dbHelper = new DataBaseUtil(ImgPosApplication.getContext());
        imageView.setImageURI(Uri.parse(dbHelper.query("中国")));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_IMG && resultCode == RESULT_OK){
            Uri uri = data.getData();
            uri_str = uri.toString();
            imageView.setImageURI(uri);
            try {
                final InputStream inputStream = ImgPosApplication.getContext().getContentResolver().openInputStream(uri);
                final ExifInterface exif = new ExifInterface(inputStream);
                final float[] latLong = new float[2];
                if(exif.getLatLong(latLong)){
                    latitude = latLong[0];
                    longitude = latLong[1];
                }
            } catch (IOException e) {
                Log.e("Error", "Error when getting location from image", e);
                showToast(e.toString());
            }
            setLocation("");
            //showToast("Longtitude:" + latitude + "\nLatitude:" + longitude);
            MapThread mapThread = new MapThread();
            mapThread.start();
        }
    }

    class MapThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (longitude == 0.0f && latitude == 0.0f){
                showToastUI("No exif information in the image.");
            }
            else {
                float[] latLong = new float[2];
                latLong[0] = latitude;
                latLong[1] = longitude;
                HttpUtil.sendRequestGetAsy(latLong, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        showToastUI("Internet error");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String str = response.body().string();
                        //子线程不能更新UI相关内容
                        runOnUiThread(() -> {
                            mapBean = HttpUtil.parseResult(str);
                            List<MapBean.AddressComponentsBean> componentsBeans =
                                    mapBean.getResults().get(0).getAddress_components();
                            setLocation("");
                            for(MapBean.AddressComponentsBean bean : componentsBeans){
                                tv_result.append(bean.getLong_name() + "\n");
                                dbHelper.insert(uri_str, bean.getLong_name());
                                dbHelper.query("中国");
                            }
                        });
                    }
                });
            }
        }
    }

    private void RequestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,}, PERMISSION_REQUEST);
            }
        }
    }

    private void setLocation(String str){
        str = str.trim();

        StringBuilder stringBuilder = new StringBuilder()
                .append("Longtitude:")
                .append(longitude)
                .append("\nLatitude:")
                .append(latitude)
                .append("\nLocation:")
                .append(str);

        tv_result.setText(stringBuilder);
    }

    private void showToast(String content){
        Toast.makeText(ImgPosApplication.getContext(), content, Toast.LENGTH_SHORT).show();
    }

    private void showToastUI(String content){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ImgPosApplication.getContext(), content, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

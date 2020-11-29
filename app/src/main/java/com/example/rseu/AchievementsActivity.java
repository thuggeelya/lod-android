package com.example.rseu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rseu.ui.gallery.GalleryFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AchievementsActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private ImageView confirmationImage = null;
    private File userdoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        String name = "";
        String category = "";
        String type = "";
        String date = "";

        if (GalleryFragment.buttonNumber >= 0) {
            name = GalleryFragment.achievements.get(GalleryFragment.buttonNumber).get("Наименование");
            category = GalleryFragment.achievements.get(GalleryFragment.buttonNumber).get("Категория");
            type = GalleryFragment.achievements.get(GalleryFragment.buttonNumber).get("Тип");
            date = GalleryFragment.achievements.get(GalleryFragment.buttonNumber).get("Дата");
        }

        TextView nameBox = findViewById(R.id.achieve_name);
        TextView categoryBox = findViewById(R.id.achieve_category);
        TextView typeBox = findViewById(R.id.lastname);
        TextView dateBox = findViewById(R.id.achieve_date);

        nameBox.setText(name);
        categoryBox.setText(category);
        typeBox.setText(type);
        dateBox.setText(date);

        confirmationImage = findViewById(R.id.confirmation_image);

        Button confirmationButton = findViewById(R.id.confirm_participation_batton);
        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                int GALLERY_REQUEST = 1;
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

                new CountDownTimer(500, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    @Override
                    public void onFinish() {
                        CountDown.start();
                    }
                }.start();
            }
        });
    }

    //Обрабатываем результат выбора в галерее
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        int pick_image = 1;
        if (requestCode == pick_image) {
            if (resultCode == RESULT_OK) {
                try {

                    //Получаем URI изображения, преобразуем его в Bitmap
                    //объект и отображаем в элементе ImageView нашего интерфейса:
                    final Uri imageUri = imageReturnedIntent.getData();
                    assert imageUri != null;
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    confirmationImage.setImageBitmap(selectedImage);

                    //selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        confirmationImage.buildDrawingCache();  //удостовериться, что imageView будет записан в кэш
        Bitmap myBit = confirmationImage.getDrawingCache(); //взять битмап из кэша
        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        myBit.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        try {
            userdoc = File.createTempFile("temp_doc", ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(userdoc);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert fos != null;
            fos.write(bitmapdata);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //documentsImages.add(confirmationImage);
    }

    private CountDownTimer CountDown = new CountDownTimer(500, 250) {

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            this.cancel();
        }
    };

    public void onBackToGalleryClick(View v) {
        Intent intent = new Intent(this, StartPage.class);
        startActivity(intent);
    }
}
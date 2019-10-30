package com.example.stoic.googleatelier_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class AddMovieActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etActors;
    private EditText etDescription;
    private RatingBar rbBar;
    private EditText etGenre;
    private EditText etYear;
    private Button btnSubmit;
    TextView textTargetUri;
    ImageView targetImage;
    private static String movie_logo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmovie);

        final myDBHandler dbHandler = new myDBHandler(this, null, null, 1);

        etName = findViewById(R.id.etMovieName);
        rbBar = findViewById(R.id.rbAdd);
        etGenre = findViewById(R.id.etMovieGenre);
        etActors = findViewById(R.id.etMovieActors);
        etYear = findViewById(R.id.etMovieYear);
        etDescription = findViewById(R.id.tvShortDes);
        btnSubmit = findViewById(R.id.btnSubmitMovie);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try {
                    System.out.println(movie_logo);
                    dbHandler.addHandler(new Movie(etName.getText().toString().trim(), rbBar.getRating(), etGenre.getText().toString().trim(),
                            etDescription.getText().toString(), etActors.getText().toString().trim(), movie_logo, null,Boolean.toString(false)));

                }catch(Exception e){
                    System.out.println("Thrown exception: " + e.getMessage());
                }
                startActivity(new Intent(AddMovieActivity.this, MoviesActivity.class));
            }
        });
        Button buttonLoadImage = (Button)findViewById(R.id.loadimage);
        textTargetUri = (TextView)findViewById(R.id.targeturi);
        targetImage = (ImageView)findViewById(R.id.targetimage);

        buttonLoadImage.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }});


    }
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            textTargetUri.setText(targetUri.toString());
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                Bitmap resized;
                resized = getResizedBitmap(bitmap,240,240);
                targetImage.setImageBitmap(resized);
                movie_logo = bitmapToBase64(resized);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}

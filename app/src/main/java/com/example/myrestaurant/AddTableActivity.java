package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

import DB.TablesDB;
import Models.Table;

public class AddTableActivity extends AppCompatActivity {
    private Button generateCodeBtn,saveTableBtn;
    private EditText tableNameTxt;
    private ImageView codeImageView;
    private TablesDB db = new TablesDB();
    private ProgressBar progressBar;
    private Table table;
    private String sID;
    private byte[] _data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);
        generateCodeBtn = findViewById(R.id.generateCodeBTN);
        saveTableBtn = findViewById(R.id.saveTableBTN);
        tableNameTxt = findViewById(R.id.tableNameTxt);
        codeImageView = findViewById(R.id.qrCodeImageView);
        progressBar = findViewById(R.id.tableprogressBar3);
        table = new Table();

        generateCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sID = db.root.push().getKey();
                MultiFormatWriter writer = new MultiFormatWriter();
                try{
                    BitMatrix matrix = writer.encode(sID, BarcodeFormat.QR_CODE,
                            350,350);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(matrix);
                    codeImageView.setImageBitmap(bitmap);
                    /*InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);*/
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                     _data = baos.toByteArray();


                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        });
        saveTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToFirebase(_data);

            }
        });
    }


    private void uploadToFirebase(byte[] data) {

        db.fileRef.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                db.fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        table.setID(sID);
                        table.setName(tableNameTxt.getText().toString());
                        table.setImageUrl(uri.toString());

                        db.root.child(sID).setValue(table);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddTableActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddTableActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
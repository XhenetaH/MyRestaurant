package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import DB.CategoriesDB;

public class AddCategoryActivity extends AppCompatActivity {

    private Button saveBtn;
    private ImageView imageView;
    private ProgressBar progressBar;
    private CategoriesDB db = new CategoriesDB();
    private Category category;
    private EditText name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        category = new Category();

        saveBtn = findViewById(R.id.saveCategoryBTN);
        imageView = findViewById(R.id.categoryImageView);
        progressBar = findViewById(R.id.progressBar);

        name = findViewById(R.id.categoryNameTXT);
        description = findViewById(R.id.categoryDescriptionTXT);
        /////////////////////

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (category.getImageUri() != null) {
                    uploadToFirebase(category.getImageUri());
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a picture!", Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            category.setImageUri(data.getData());
            imageView.setImageURI(category.getImageUri());
        }
    }


    private void uploadToFirebase(Uri uri) {

        db.fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                db.fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String modelId = db.root.push().getKey();
                        Category model = new Category(modelId, name.getText().toString(), description.getText().toString(), uri.toString());
                        db.root.child(modelId).setValue(model);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddCategoryActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddCategoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*private String getFileExtension(Uri mUri)
    {
        String fileExt = MimeTypeMap.getFileExtensionFromUrl(mUri.toString());
        return fileExt;
    }*/
}
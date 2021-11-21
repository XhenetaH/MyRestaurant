package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import DB.CategoriesDB;
import DB.MenuItemDB;
import Models.MenuItem;

public class AddMenuItemActivity extends AppCompatActivity {
    private CategoriesDB db = new CategoriesDB();
    private MenuItemDB menuDB = new MenuItemDB();
    private AutoCompleteTextView autoCompleteText;
    private ArrayAdapter<String> adapterItems;
    private ArrayList<String> categoryItems;
    private ImageView imageView;
    private Uri imageURI;
    private EditText nameTXT, descriptionTXT, priceTXT;
    private ProgressBar progressBar;
    private Button saveBtn;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);
        autoCompleteText = findViewById(R.id.categoryautoCompleteTextView);
        categoryItems = new ArrayList<>();
        imageView = findViewById(R.id.menuItemImageView);
        nameTXT = findViewById(R.id.nameMenuTXT);
        descriptionTXT = findViewById(R.id.descriptionMenuTXT);
        priceTXT = findViewById(R.id.priceMenuTXT);
        progressBar = findViewById(R.id.menuProgressBar);
        saveBtn = findViewById(R.id.saveMenuBTN);
        menuItem = new MenuItem();

        //#region qasja ne gallery te paisjes mobile
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
        //#endregion
        //#region Insertimi i te dhenave ne databaze
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageURI != null) {
                    uploadToFirebase(imageURI);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a picture!", Toast.LENGTH_LONG);
                }
            }
        });
        //#endregion

        //#region Te dhenat per dropdown listen e kategorive
        GetDataFromFirebase();


        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                menuItem.setCategory(adapterView.getItemAtPosition(i).toString());
            }
        });
        //#endregion
    }

    //#region Marrja e Uri dhe shfaqja e fotos se zgjedhur
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageURI= data.getData();
            imageView.setImageURI(imageURI);
        }
    }
    //#endregion
    //#region Metoda per insertimin e te dhenave ne databaze
    private void uploadToFirebase(Uri uri) {
        menuDB.fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                menuDB.fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String modelId = menuDB.root.push().getKey();

                        menuItem.setName(nameTXT.getText().toString());
                        menuItem.setDescription(descriptionTXT.getText().toString());
                        menuItem.setImageUrl(uri.toString());
                        menuItem.setPrice(Double.parseDouble(priceTXT.getText().toString()));
                        menuItem.setId(modelId);

                        menuDB.root.child(modelId).setValue(menuItem);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddMenuItemActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AddMenuItemActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }//#endregion

    //#region Metoda per marrjen e kategorive nga databasa
    private void GetDataFromFirebase(){
        Query query = db.root;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapsh : snapshot.getChildren()){
                    categoryItems.add(snapsh.child("name").getValue().toString());
                }
                adapterItems = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,categoryItems);
                autoCompleteText.setAdapter(adapterItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //#endregion
}
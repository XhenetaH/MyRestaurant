package com.example.myrestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DB.CategoriesDB;
import DB.MenuItemDB;

public class UDMenuItemActivity extends AppCompatActivity {
    private MenuItemDB db = new MenuItemDB();
    private CategoriesDB categoriesDB = new CategoriesDB();
    private EditText nameTxt,descriptionTxt,priceTxt;
    private ImageView imageV;
    private Button updateBtn, deleteBtn;
    private Uri uri;
    private ProgressBar progressBar;
    private AutoCompleteTextView autoCompleteText;
    private String newCategory;

    private ArrayAdapter<String> adapterItems;
    private ArrayList<String> categoryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udmenu_item);
        progressBar = findViewById(R.id.UDprogressBar2);
        nameTxt = findViewById(R.id.udNameMenuTXT);
        descriptionTxt = findViewById(R.id.udDescriptionMenuTXT);
        priceTxt = findViewById(R.id.udPriceMenuTXT);
        imageV = findViewById(R.id.udMenuItemIV);
        updateBtn = findViewById(R.id.uDupdateBtn);
        deleteBtn = findViewById(R.id.uDdeleteBtn);
        autoCompleteText = findViewById(R.id.menuitemAutoCompleteTextView);
        categoryItems = new ArrayList<>();

        String name = getIntent().getStringExtra("Name");
        String ID = getIntent().getStringExtra("id");
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("Image");
        String category = getIntent().getStringExtra("category");
        String price = getIntent().getStringExtra("price");
        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("Image")).into(imageV);

        nameTxt.setText(name);
        descriptionTxt.setText(description);
        priceTxt.setText(price);
        autoCompleteText.setText(category);
        imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,2);
            }
        });
        autoCompleteText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if(item==null){
                    newCategory = category;
                }else{
                    newCategory=item;
                }
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile(ID,imageUrl);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UDMenuItemActivity.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.root.child(ID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(UDMenuItemActivity.this,"Deleted successfully!",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UDMenuItemActivity.this,"Failed!",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(UDMenuItemActivity.this,"Cancelled.",Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
        GetDataFromFirebase();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && resultCode == RESULT_OK && data!=null){
            uri = data.getData();
            imageV.setImageURI(uri);
        }
    }
    private void uploadFile(String ID,String imageUrl){
        if(uri!=null){
            db.fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    db.fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Map<String,Object> map = new HashMap<>();
                            map.put("imageUrl",uri.toString());
                            map.put("name",nameTxt.getText().toString());
                            map.put("description",descriptionTxt.getText().toString());
                            map.put("price",priceTxt.getText().toString());
                            map.put("category",newCategory);

                            db.root.child(ID).updateChildren(map);
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Map<String,Object> map = new HashMap<>();
            map.put("imageUrl",imageUrl);
            map.put("name",nameTxt.getText().toString());
            map.put("description",descriptionTxt.getText().toString());
            map.put("price",priceTxt.getText().toString());
            map.put("category",newCategory);

            db.root.child(ID).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    //#region Metoda per marrjen e kategorive nga databasa
    private void GetDataFromFirebase(){
        Query query = categoriesDB.root;
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapsh : snapshot.getChildren()){
                    categoryItems.add(snapsh.child("name").getValue().toString());
                }
                adapterItems = new ArrayAdapter<String>(UDMenuItemActivity.this,R.layout.dropdown_item,categoryItems);
                autoCompleteText.setAdapter(adapterItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //#endregion
}
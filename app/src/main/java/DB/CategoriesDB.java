package DB;

import android.app.Notification;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myrestaurant.AddCategoryActivity;
import com.example.myrestaurant.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CategoriesDB {
    public StorageReference reference = FirebaseStorage.getInstance().getReference();
    public DatabaseReference root = FirebaseDatabase.getInstance().getReference("Categories");
    public StorageReference fileRef = reference.child(System.currentTimeMillis()+".");

    public CategoriesDB(){


    }

   /* public boolean add(Category category){
        return root.child(category.getID()).setValue(category).isSuccessful();
    }

    public int addFile(Uri uri){

        if(uri!=null) {
            int[] result = {0};
            fileRef.putFile(uri).addOnCompleteListener(task ->{
               result[0]=2;;
            }).addOnProgressListener(tast ->
            {
               result[0]=1;
            });
            return result[0];
        }
        else
            return -1;

    }

    private String getFileExtension(Uri mUri)
    {
        String fileExt = MimeTypeMap.getFileExtensionFromUrl(mUri.toString());
        return fileExt;
    }*/
}

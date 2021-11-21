package DB;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MenuItemDB {
    public StorageReference reference = FirebaseStorage.getInstance().getReference();
    public DatabaseReference root = FirebaseDatabase.getInstance().getReference("MenuItems");
    public StorageReference fileRef = reference.child(System.currentTimeMillis()+".");
}

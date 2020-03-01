package jumana.aslan.com.Data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseUtils {
    public static FirebaseAuth auth=FirebaseAuth.getInstance();
    public static FirebaseDatabase db=FirebaseDatabase.getInstance();
    public static DatabaseReference getReferance()
    {
        String uid = auth.getUid();
        return db.getReference().child("Symptoms").child(uid);

    }
}

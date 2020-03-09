package jumana.aslan.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jumana.aslan.com.Data.MySymptoms;

public class Add_Photo extends AppCompatActivity {
    private EditText edtPohotoTittle;
    private Button btnphotosave;
    private ImageButton uploadPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__photo);

        edtPohotoTittle=findViewById(R.id.edtPohotoTittle);
        btnphotosave=findViewById(R.id.btnphotosave);
        uploadPhoto=findViewById(R.id.uploadPhoto);

    }
    private void dataHandler()
    {
        String tittle=edtPohotoTittle.getText().toString();
        boolean isOk=true;

        if(tittle.length()<1)
        {
            edtPohotoTittle.setError("tittle length is error");
            isOk=false;
        }

        if (isOk)
        {
            MySymptoms sy=new MySymptoms();
            sy.setTittle(tittle);
            createSymptom(sy);
            createSymptom(tittle);

        }

    }
    private void createSymptom(MySymptoms f)
    {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        sy.setOwner(uid);
        String key = reference.child("symptoms").push().getKey();
        reference.child("symptoms").child(uid).child(key).setValue(sy).addOnCompleteListener(Add_Photo.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(Add_Photo.this, "add successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(Add_Photo.this, "add failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }

        });

    }
    private void createSymptom(String tittle, String subject, int rate)
    {
    }
}

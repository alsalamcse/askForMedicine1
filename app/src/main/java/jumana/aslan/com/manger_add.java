package jumana.aslan.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import jumana.aslan.com.Data.MySymptoms;

public class manger_add extends AppCompatActivity {
    private EditText manTitle,manSub;
    private Button manSave;
    private FloatingActionButton mangerAddPhoto;
    private RatingBar manRatingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_add);

        manTitle=findViewById(R.id.manTitle);
        manSub=findViewById(R.id.manSub);
        manSave=findViewById(R.id.manSave);
        mangerAddPhoto=findViewById(R.id.mangerAddPhoto);

    }

    private void dataHandler()
    {
        String tittle=manTitle.getText().toString();
        String subject=manSub.getText().toString();
        int rate=manRatingBar.getProgress();
        boolean isOk=true;

        if(tittle.length()<1)
        {
            manTitle.setError("tittle length is error");
            isOk=false;
        }
        if(subject.length()<1)
        {
            manSub.setError("subject length error");
        }
        if (isOk)
        {
            MySymptoms m=new MySymptoms();
            sy.setTittle(tittle);
            createSymptom(sy);
            createSymptom(tittle,subject,rate);

        }

    }
    private void createSymptom(MySymptoms sy)
    {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        sy.setOwner(uid);
        String key = reference.child("symptoms").push().getKey();
        reference.child("symptoms").child(uid).child(key).setValue(sy).addOnCompleteListener(manger_add.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(manger_add.this, "add successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(manger_add.this, "add failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }

        });

    }
    private void createSymptom(String tittle, String subject, int rate)
    {
    }
}



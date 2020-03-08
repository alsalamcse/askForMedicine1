package jumana.aslan.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class add_symptoms extends AppCompatActivity {
    private EditText symTitle,symSub;
    private Button symSave;
    private RatingBar ratBar;
    private CheckBox chboxDelete,itmisManger;
    private Object mySymptoms;
    private FloatingActionButton clientAddPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptoms);

        symTitle=findViewById(R.id.symTitle);
        symSub=findViewById(R.id.symSub);
        ratBar=findViewById(R.id.ratBar);
        symSave=findViewById(R.id.symSave);
        clientAddPhoto=findViewById(R.id.clientAddPhoto);

        symSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHandler();
            }

        });
    }
    private void dataHandler()
    {
        String tittle=symTitle.getText().toString();
        String subject=symSub.getText().toString();
        int rate=ratBar.getProgress();
        boolean isOk=true;

        if(tittle.length()<1)
        {
           symTitle.setError("tittle length is error");
            isOk=false;
        }
        if(subject.length()<1)
        {
           symSub.setError("subject length error");
        }
        if (isOk)
        {
            MySymptoms sy=new MySymptoms();
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
        reference.child("symptoms").child(uid).child(key).setValue(sy).addOnCompleteListener(add_symptoms.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(add_symptoms.this, "add successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(add_symptoms.this, "add failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }

        });

    }
    private void createSymptom(String tittle, String subject, int rate)
    {
    }
}


//
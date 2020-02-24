package jumana.aslan.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class add_symptoms extends AppCompatActivity {
    private EditText symTitle,symSub;
    private Button symSave;
    private RatingBar ratBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptoms);

        symTitle=findViewById(R.id.symTitle);
        symSub=findViewById(R.id.symSub);
        ratBar=findViewById(R.id.ratBar);
        symSave=findViewById(R.id.symSave);


        symSave.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                dataHandler();
            }

            private void dataHandler()
            {

            }
        });

    }
    private void dataHandler() {
        boolean isok = true;
        String Title = symTitle.getText().toString();
        String Subject = symSub.getText().toString();
        int priority = ratBar.getProgress();

        if (Title.length() == 0) {
            symTitle.setError("Enter Title");
            isok = false;
        }
        if (Subject.length() == 0) {
            symSub.setError("Enter Subject");
            isok = false;
        }
        if (isok) {
            Task t = new Task();
            t.setTitle(Title);
            t.setSub(Subject);
            t.setPriority(priority);

            createTask(t);

        }
    }

    //save data base
    private void createTask(Task t) {
        //1

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //2
        DatabaseReference reference = database.getReference();
        //to get the user id (or other details like email)
        FirebaseAuth auth=FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        t.setOwner(uid);


        String key = reference.child("tasks").push().getKey();
        t.setKey(key);
        reference.child("tasks").child(uid).child(key).setValue(t).addOnCompleteListener(addTask.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(addTask.this, "Add ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(addTask.this, "Add Failed"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException(). printStackTrace();
                }
            }
        });
    }



}


//
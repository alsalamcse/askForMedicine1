package jumana.aslan.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import jumana.aslan.com.Data.MySymptoms;

public class add_symptoms extends AppCompatActivity {
    private EditText symTitle,symSub;
    private Button symSave;
    private RatingBar symptomRatBar;
    private CheckBox chboxDelete,itmisManger;
    private Object mySymptoms;
    private FloatingActionButton clientAddPhoto;
    //upload: 0.1 add firebase storage
    //upload: 0.2 add this per,issions to manifest xml
//          <uses-permission android:name="android.permission.INTERNET" />
//          <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

    //upload: 1 add Xml image view or butn and uplaod button
    //upload:

    private ImageButton imgBtnl;
    private Button btnUpload;
    private TextView tvImgUrl;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptoms);
        //upload: 3
        imgBtnl=findViewById(R.id.imgBtn);
        btnUpload=findViewById(R.id.btnUpload);
        tvImgUrl=findViewById(R.id.tvImgURL);

        //upload: 4
        imgBtnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), 99);
            }
        });
        ////upload: 6
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        symTitle=findViewById(R.id.symTitle);
        symSub=findViewById(R.id.symSub);
        symptomRatBar=findViewById(R.id.symptomRatBar);
        symSave=findViewById(R.id.symSave);
        clientAddPhoto=findViewById(R.id.clientAddPhoto);

        symSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHandler();
            }

        });
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            FirebaseStorage storage= FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 99 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgBtnl.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void dataHandler()
    {
        String tittle=symTitle.getText().toString();
        String subject=symSub.getText().toString();
        int rate=symptomRatBar.getProgress();
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
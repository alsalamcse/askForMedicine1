package jumana.aslan.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUp extends AppCompatActivity {
    private EditText edtName,edtFName,edtphone,edtEmail,edtPass,edtRePass;
    private Button btnS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtFName = findViewById(R.id.edtFName);
        edtName = findViewById(R.id.edtName);
        edtphone = (EditText) findViewById(R.id.edtphone);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText) findViewById(R.id.edtPassword);
        edtRePass = (EditText) findViewById(R.id.edtRePass);
        btnS = (Button) findViewById(R.id.btnS);

        btnS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                datahandler();
            }
            private void datahandler() {
                String first = edtFName.getText().toString();
                String Name = edtName.getText().toString();
                String phone = edtphone.getText().toString();
                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();
                String repass = edtRePass.getText().toString();
                boolean isOk = true;
                if (email.length() < 4 || email.indexOf('@') < 0 || email.indexOf('.') < 0) {
                    edtEmail.setError(("wrong email"));
                    isOk = false;
                }
                if (pass.length() < 8 || repass.equals(pass) == false) {
                    edtRePass.setError("Have to be at least 8 char");
                    edtPass.setError("Have to be at least 8 char");
                    isOk = false;
                }
                if (first.length() == 0) {
                    edtFName.setError("enter name ");
                    isOk = false;
                }
                if (isOk) {
                    createAccount(email, pass, repass, first, phone);
                    //create Accoint (email,pass)
                }
            }

        });
    }





    private void createAccount(String email, String pass, String repass, final String first, String phone)
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(signUp.this, "Sign up succesful", Toast.LENGTH_SHORT).show();
                    finish();;
                }
                else
                {
                    edtEmail.setError("Sign up failed");
                }
            }
        });
    }
    }


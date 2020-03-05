package jumana.aslan.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signIn extends AppCompatActivity {
    private EditText edtEmail,edtPassword;
    private Button btnIn,btnUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword) ;
        btnIn=(Button)findViewById(R.id.btnIn);
        btnUp=(Button)findViewById(R.id.btnUp);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),signUp.class);
                startActivity(i);

            }
        });
        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataHandler();
            }
        });
    }

    private void dataHandler()//
    {
        String email=edtEmail.getText().toString();
        String passw=edtPassword.getText().toString();
        boolean isOk=true;
        if(email.length()<4)
        {
            edtEmail.setError("Email length error");
            isOk=false;
        }
        if(email.indexOf("@")<0 || email.indexOf(".")<0)
        {
            edtEmail.setError("email wrong format");
            isOk=false;
        }
        //if ((isValidEmailAddress(email)==false))
        //{
        // edtEmail.setError("Invalid Email");
        // isOk=false;
        //  }
        if(passw.length()<8)
        {  edtPassword.setError("password length is Error");
            isOk=false;
        }
        if(isOk)
        {
            signIn(email,passw);
        }
    }
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    private void signIn(final String email, String passw)
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,passw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //todo go to main screen (all task activity)
                    Intent intent= new Intent(getApplication(),Search.class);
                    startActivity(intent);
                }
                else
                {
                    edtEmail.setError("Email or passwod is wrong");


                }


            }
        });

    }}


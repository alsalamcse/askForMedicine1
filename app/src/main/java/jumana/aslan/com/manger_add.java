package jumana.aslan.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class manger_add extends AppCompatActivity {
    private EditText manTitle,manSub;
    private Button manSave;
    private FloatingActionButton mangerAddPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_add);

        manTitle=findViewById(R.id.manTitle);
        manSub=findViewById(R.id.manSub);
        manSave=findViewById(R.id.manSave);
        mangerAddPhoto=findViewById(R.id.mangerAddPhoto);

    }
}



package jumana.aslan.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class add_symptoms extends AppCompatActivity {
    private EditText symTitle,symSub;
    private Button symSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_symptoms);

        symTitle=findViewById(R.id.symTitle);
        symSub=findViewById(R.id.symSub);
        symSave=findViewById(R.id.symSave);

    }
}
//
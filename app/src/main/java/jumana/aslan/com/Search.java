package jumana.aslan.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private SearchView searchV;
    private TextView tvPeo,tvExp;
    private FloatingActionButton addPeo,addExp;
    private ListView listV;

    ArrayList<String>list;
    ArrayAdapter<String>adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchV=findViewById(R.id.searchV);
        tvExp=findViewById(R.id.tvExp);
        tvPeo=findViewById(R.id.tvPeo);
        addExp=findViewById(R.id.addExp);
        addPeo=findViewById(R.id.addPeo);
        listV=findViewById(R.id.listV);


    }
}

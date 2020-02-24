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
    private ListView myList;

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
        myList=findViewById(R.id.myList);

        list=new ArrayList<String>();

        list.add("وصفة لالام الراس");
        list.add("وصفة لالام الظهر");
        list.add("وصفة لالام البطن");
        list.add("وصفة لالام القدمين");
        list.add("وصفة لالتهابات الحلق");
        list.add("وصفة لالام المفاصل");

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        myList.setAdapter(adapter);
        searchV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });


    }
}

package jumana.aslan.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    private TextView tvPeo,tvExp;
    private FloatingActionButton addPeo,addExp;
    private ListView myList;
    private Button moveToTabs;

    ArrayList<String>list;
   ArrayAdapter<String>adapter;



   // @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tvExp=findViewById(R.id.tvExp);
        tvPeo=findViewById(R.id.tvPeo);
        addExp=findViewById(R.id.addExp);
        addPeo=findViewById(R.id.addPeo);
        myList=findViewById(R.id.myList);
        moveToTabs=findViewById(R.id.moveToTabs);

        moveToTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),MainSymptoms.class);
                startActivity(i);


                list=new ArrayList<String>();

        list.add("asa");
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
        addPeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add People medicine", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent =new Intent (getApplication(), add_symptoms.class);
                startActivity(intent);
            }
        });
        addExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Add Expert medicine", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent =new Intent (getApplication(), add_symptoms.class);
                startActivity(intent);
            }
        });

}}

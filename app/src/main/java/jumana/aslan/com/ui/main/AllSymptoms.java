package jumana.aslan.com.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jumana.aslan.com.Data.MySymptoms;
import jumana.aslan.com.Data.SymotomsAdapter;
import jumana.aslan.com.R;

public class AllSymptoms extends Fragment {
    private SymotomsAdapter symotomsAdapter;
    private ListView listView;

    //0 search: add ET amd Btn to xml
    //1 search:
    private ImageView imSearch;
    private EditText etTitleTosearch;


    public AllSymptoms() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        symotomsAdapter=new SymotomsAdapter(getContext());
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_all_symptoms, container, false);
        listView=view.findViewById(R.id.lstSymptoms);
        listView.setAdapter(symotomsAdapter);

        //2 search:
        imSearch=view.findViewById(R.id.imSearch);
        etTitleTosearch=view.findViewById(R.id.etTitleTosearch);
        //3 search event
        imSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSearch= etTitleTosearch.getText().toString();
                //5 search
                readTaskFromFireBase(toSearch);

            }
        });



        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        //6 search: delete method calling
        //readTaskFromFireBase("");
    }
    public void readTaskFromFireBase(final String stToSearch)
    {

        FirebaseDatabase database=FirebaseDatabase.getInstance();//to get current Uid
        FirebaseAuth auth1=FirebaseAuth.getInstance();
        String uid = auth1.getUid();
        DatabaseReference reference=database.getReference();

        reference.child("symptoms").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                symotomsAdapter.clear();;
                for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    MySymptoms t = d.getValue(MySymptoms.class);
                    Log.d("MySymptoms", t.toString());
                    //5 search:
                    if(stToSearch==null || stToSearch.length()==0)
                    {
                        symotomsAdapter.add(t);
                    }
                    else //6 search:
                        {
                            if(t.getTittle().contains(stToSearch))
                                symotomsAdapter.add(t);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

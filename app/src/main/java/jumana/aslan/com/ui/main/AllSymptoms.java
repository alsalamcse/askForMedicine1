package jumana.aslan.com.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        readTaskFromFireBase();
    }

    public void readTaskFromFireBase()
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
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    MySymptoms t = d.getValue(MySymptoms.class);
                    Log.d("MySymptoms",t.toString());
                    symotomsAdapter.add(t);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

package jumana.aslan.com.Data;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import jumana.aslan.com.R;

import static android.widget.Toast.LENGTH_SHORT;

public class TaskAdapter extends ArrayAdapter<MySymptoms>
{
    public TaskAdapter(@NonNull Context context) {
        super(context, R.layout.itemremedy);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View vitem = LayoutInflater.from(getContext()).inflate(R.layout.itemremedy, parent, false);
        TextView tvTitle = vitem.findViewById(R.id.itmTvTitle);
        TextView tvSubject = vitem.findViewById(R.id.itmTvSub);
        RatingBar rbPrio = vitem.findViewById(R.id.ratingBar3);
        CheckBox cbIsCompleted = vitem.findViewById(R.id.itmisManger);
        final ImageView ivInfo = vitem.findViewById(R.id.itmImginfo);

        final MySymptoms mySymptoms = getItem(position);
        //todo טיפול באירוע מחיקה
        cbIsCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //todo delete this item
                    FireBaseUtils.getReferance().child(mySymptoms.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                Toast.makeText(getContext(), "deleted", LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(getContext(), "not deleted" + databaseError.getMessage(), LENGTH_SHORT).show();

                            }
                        }
                    });



                }
            }
        });
          ivInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), mySymptoms.getTittle(), LENGTH_SHORT).show();
                  ShowMenu();


                }

          });

        MySymptoms symptoms = getItem(position);
        tvTitle.setText(symptoms.getTittle());
        tvSubject.setText(symptoms.getSubject());
        rbPrio.setRating(symptoms.getImportant());
        cbIsCompleted.setChecked(false);
        return vitem;
    }
    public void ShowMenu() {

        final String[] option = {"Add", "View", "Select", "Delete"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item);
        adapter.addAll(option);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select op   tion");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (i == 0) {
                    Toast.makeText(getContext(), "Add", Toast.LENGTH_SHORT).show();
                }
                if (i == 1) {
                    Toast.makeText(getContext(), "View", Toast.LENGTH_SHORT).show();
                }
                if (i == 2) {
                    Toast.makeText(getContext(), "Select", Toast.LENGTH_SHORT).show();
                }
                if (i == 3) {
                    Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
                }


            }
        });
        final AlertDialog a = builder.create();
        a.show();
    }
}
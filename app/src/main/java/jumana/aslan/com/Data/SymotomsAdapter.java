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

public class SymotomsAdapter extends ArrayAdapter<MySymptoms>
{
    public SymotomsAdapter(@NonNull Context context) {
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
        CheckBox checkBoxdelete = vitem.findViewById(R.id.chboxDelete);
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

        cbIsCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FireBaseUtils.getReferance().child(mySymptoms.getKey()).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                Toast.makeText(getContext(), "delete", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), "not deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });






        MySymptoms symptoms = getItem(position);
        tvTitle.setText(symptoms.getTittle());
        tvSubject.setText(symptoms.getSubject());
        rbPrio.setRating(symptoms.getImportant());
        cbIsCompleted.setChecked(false);
        return vitem;
    }




}
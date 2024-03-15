package uk.ac.wlv.criminalintent;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    Button mDateButton;
    CheckBox mSolvedCheckBox;
    private SharedPreferences mSharedPreferences;

    private static final String PREFS_NAME = "CrimeFragmentPrefs";
    private static final String  LAST_ENTERED_TEXT_KEY = "lastEnteredText";

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        mCrime = new Crime();
        mSharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
//    public void onCreate(Bundle state){
//        super.onCreate(state);
//        mCrime = new Crime();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = v.findViewById(R.id.crime_title);

        String lastEnteredText = mSharedPreferences.getString(LAST_ENTERED_TEXT_KEY, "");
        mTitleField.setText(lastEnteredText);

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle((s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveLastEnteredText(s.toString());
            }
        });
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        Date date = mCrime.getDate();
        SimpleDateFormat  dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        String fomatDate = dateFormat.format(date);
        mDateButton.setText(fomatDate);
        mDateButton.setEnabled(false);
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }
    private void saveLastEnteredText(String text){
        SharedPreferences.Editor editor  = mSharedPreferences.edit();
        editor.putString(LAST_ENTERED_TEXT_KEY, text);
        editor.apply();
    }

}

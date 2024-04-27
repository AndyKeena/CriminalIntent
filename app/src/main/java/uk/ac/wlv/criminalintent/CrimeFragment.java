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
import java.util.UUID;

public class CrimeFragment extends Fragment {
    private Crime mCrime;
    private EditText mTitleField;
    Button mDateButton;
    CheckBox mSolvedCheckBox;
    private SharedPreferences mSharedPreferences;
    private static final String ARG_CRIME_ID ="crime_id";
    private static final String PREFS_NAME = "CrimeFragmentPrefs";
    private static final String  LAST_ENTERED_TEXT_KEY = "lastEnteredText";

    public static CrimeFragment newInstance(UUID crime_id){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crime_id);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
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
            }
        });
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        Date date = mCrime.getDate();
        SimpleDateFormat  dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        String fomatDate = dateFormat.format(date);
        mDateButton.setText(fomatDate);
        mDateButton.setEnabled(false);
        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }
}

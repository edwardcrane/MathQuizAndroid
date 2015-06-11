package com.airanza.mathquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class ProblemSettingsFragment extends PreferenceFragment implements View.OnClickListener {
	static final String INCLUDE_ADDITION = "includeAddition";
	static final String INCLUDE_SUBTRACTION = "includeSubtraction";
	static final String INCLUDE_MULTIPLICATION = "includeMultiplication";
	static final String INCLUDE_DIVISION = "includeDivision";
	static final String INCLUDE_NEGATIVE_NUMBERS = "includeNegativeNumbers";

	private CheckBox additionCheckBox = null;
	private CheckBox subtractionCheckBox = null;
	private CheckBox multiplicationCheckBox = null;
	private CheckBox divisionCheckBox = null;
	private CheckBox negativeNumbersCheckBox = null;

	private MathQuizSettings mathQuizSettings = null;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.problem_settings_fragment,  container, false);

		Button b = (Button) v.findViewById(R.id.clearStatisticsButton);
		b.setOnClickListener(this);

		additionCheckBox = (CheckBox)v.findViewById(R.id.additionCheckBox);
		additionCheckBox.setOnClickListener(this);

		subtractionCheckBox = (CheckBox)v.findViewById(R.id.subtractionCheckBox);
		subtractionCheckBox.setOnClickListener(this);

		multiplicationCheckBox = (CheckBox)v.findViewById(R.id.multiplicationCheckBox);
		multiplicationCheckBox.setOnClickListener(this);

		divisionCheckBox = (CheckBox)v.findViewById(R.id.divisionCheckBox);
		divisionCheckBox.setOnClickListener(this);

		negativeNumbersCheckBox = (CheckBox)v.findViewById(R.id.negativeNumbersCheckBox);
		negativeNumbersCheckBox.setOnClickListener(this);

		if(savedInstanceState != null)
		{
			// Restore value of members from saved state:
			mathQuizSettings.setIncludeAddition(savedInstanceState.getBoolean(INCLUDE_ADDITION));
			mathQuizSettings.setIncludeSubtraction(savedInstanceState.getBoolean(INCLUDE_SUBTRACTION));
			mathQuizSettings.setIncludeMultiplication(savedInstanceState.getBoolean(INCLUDE_MULTIPLICATION));
			mathQuizSettings.setIncludeDivision(savedInstanceState.getBoolean(INCLUDE_DIVISION));
			mathQuizSettings.setIncludeNegativeNumbers(savedInstanceState.getBoolean(INCLUDE_NEGATIVE_NUMBERS));
		}

		return v;
	}

	public void setMathQuizSettings(MathQuizSettings settings) {
		mathQuizSettings = settings;
		additionCheckBox.setChecked(mathQuizSettings.isIncludeAddition());
		subtractionCheckBox.setChecked(mathQuizSettings.isIncludeSubtraction());
		multiplicationCheckBox.setChecked(mathQuizSettings.isIncludeMultiplication());
		divisionCheckBox.setChecked(mathQuizSettings.isIncludeDivision());
		negativeNumbersCheckBox.setChecked(mathQuizSettings.isIncludeNegativeNumbers());
	}

	public MathQuizSettings getMathQuizSettings() {
		mathQuizSettings.setIncludeAddition(additionCheckBox.isChecked());
		mathQuizSettings.setIncludeSubtraction(subtractionCheckBox.isChecked());
		mathQuizSettings.setIncludeMultiplication(multiplicationCheckBox.isChecked());
		mathQuizSettings.setIncludeDivision(divisionCheckBox.isChecked());
		mathQuizSettings.setIncludeNegativeNumbers(negativeNumbersCheckBox.isChecked());
		return(mathQuizSettings);
	}

    private boolean isAtLeastOneChecked() {
        return(additionCheckBox.isChecked() || subtractionCheckBox.isChecked() || multiplicationCheckBox.isChecked() || divisionCheckBox.isChecked());
    }

	@Override
	public void onClick(View v) {

		switch(v.getId()) {
			case R.id.clearStatisticsButton:
				onClearStatistics(v);
				break;
            case R.id.negativeNumbersCheckBox:
                onCheckNegativeNumbersCheckBox(v);
                break;
			case R.id.additionCheckBox:
			case R.id.subtractionCheckBox:
			case R.id.multiplicationCheckBox:
			case R.id.divisionCheckBox:
                if(isAtLeastOneChecked()) {
                    getMathQuizSettings();
                } else {
                    Toast t = Toast.makeText(v.getContext(), "At least one operation ( +, -, *, / ) must be selected!", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();
                    // revert check box to original
                    ((CheckBox)v).setChecked(!((CheckBox)v).isChecked());
                }
                break;
			default:
				// do nothing.
		}
	}

    public void onCheckNegativeNumbersCheckBox(View view)
    {
        mathQuizSettings.setIncludeNegativeNumbers(((CheckBox) view).isChecked());
    }

	protected void onClearStatistics(View view) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        alertDialog.setTitle(getText(R.string.clear_statistics_confirm_title));
        alertDialog.setMessage(getText(R.string.clear_statistics_confirm_message));
        alertDialog.setIcon(getResources().getDrawable(android.R.drawable.ic_menu_delete));

        alertDialog.setNegativeButton(getText(R.string.clear_statistics_dialog_negative_button_text),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
//                        Toast.makeText(getActivity().getApplicationContext(), "@string/clear_statistics_dialog_statistics_not_cleared_text", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity().getApplicationContext(), R.string.clear_statistics_dialog_statistics_not_cleared_text, Toast.LENGTH_SHORT).show();
                    }
                });

        alertDialog.setPositiveButton(getText(R.string.clear_statistics_dialog_positive_button_text),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.w(this.getClass().getName(), getText(R.string.clear_statistics_dialog_statistics_cleared_text).toString());
                        mathQuizSettings.setnRight(0);
                        mathQuizSettings.setnWrong(0);
                        Toast.makeText(getActivity().getApplicationContext(), R.string.clear_statistics_dialog_statistics_cleared_text, Toast.LENGTH_LONG).show();
                    }
                });


        final AlertDialog alert = alertDialog.create();
        alert.show();
	}
}

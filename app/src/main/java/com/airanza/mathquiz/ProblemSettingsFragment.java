package com.airanza.mathquiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.airanza.mathquiz.mathproblems.ProblemGenerator;

public class ProblemSettingsFragment extends PreferenceFragment implements View.OnClickListener {
	static final String INCLUDE_ADDITION = "includeAddition";
	static final String INCLUDE_SUBTRACTION = "includeSubtraction";
	static final String INCLUDE_MULTIPLICATION = "includeMultiplication";
	static final String INCLUDE_DIVISION = "includeDivision";
	static final String INCLUDE_NEGATIVE_NUMBERS = "includeNegativeNumbers";

	private ProblemGenerator pg = new ProblemGenerator();

	private CheckBox additionCheckBox = null;
	private CheckBox subtractionCheckBox = null;
	private CheckBox multiplicationCheckBox = null;
	private CheckBox divisionCheckBox = null;
	private CheckBox negativeNumbersCheckBox = null;

	public ProblemGenerator getProblemGenerator() {
		return(this.pg);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.problem_settings_fragment,  container, false);

		Button b = (Button) v.findViewById(R.id.clearStatisticsButton);
		b.setOnClickListener(this);
		b = (Button)v.findViewById(R.id.okButton);
		b.setOnClickListener(this);
		b = (Button)v.findViewById(R.id.cancelButton);
		b.setOnClickListener(this);

		if(savedInstanceState != null)
		{
			// Restore value of members from saved state:
			pg.setIncludeAddition(savedInstanceState.getBoolean(INCLUDE_ADDITION));
			pg.setIncludeSubtraction(savedInstanceState.getBoolean(INCLUDE_SUBTRACTION));
			pg.setIncludeMultiplication(savedInstanceState.getBoolean(INCLUDE_MULTIPLICATION));
			pg.setIncludeDivision(savedInstanceState.getBoolean(INCLUDE_DIVISION));
			pg.setIncludeNegativeNumbers(savedInstanceState.getBoolean(INCLUDE_NEGATIVE_NUMBERS));
		}

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

		return v;
	}

	public void setVarsFromSharedPreferences(SharedPreferences prefs){
		pg.setIncludeAddition(prefs.getBoolean(INCLUDE_ADDITION, true));
		additionCheckBox.setChecked(pg.getIncludeAddition());
		pg.setIncludeSubtraction(prefs.getBoolean(INCLUDE_SUBTRACTION, true));
		subtractionCheckBox.setChecked(pg.getIncludeSubtraction());
		pg.setIncludeMultiplication(prefs.getBoolean(INCLUDE_MULTIPLICATION, true));
		multiplicationCheckBox.setChecked(pg.getIncludeMultiplication());
		pg.setIncludeDivision(prefs.getBoolean(INCLUDE_DIVISION, true));
		divisionCheckBox.setChecked(pg.getIncludeDivision());
		pg.setIncludeNegativeNumbers(prefs.getBoolean(INCLUDE_NEGATIVE_NUMBERS, true));
		negativeNumbersCheckBox.setChecked(pg.getIncludeNegativeNumbers());
	}

	public void saveVarsToSharedPreferences(SharedPreferences prefs) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(INCLUDE_ADDITION, pg.getIncludeAddition());
		editor.putBoolean(INCLUDE_SUBTRACTION, pg.getIncludeSubtraction());
		editor.putBoolean(INCLUDE_MULTIPLICATION, pg.getIncludeMultiplication());
		editor.putBoolean(INCLUDE_DIVISION, pg.getIncludeDivision());
		editor.putBoolean(INCLUDE_NEGATIVE_NUMBERS, pg.getIncludeNegativeNumbers());
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.clearStatisticsButton:
				onClearStatistics(v);
				break;
			case R.id.okButton:
				onOKSettings(v);
				break;
			case R.id.cancelButton:
				onCancelSettings(v);
				break;
			case R.id.additionCheckBox:
				onCheckAdditionCheckBox(v);
				break;
			case R.id.subtractionCheckBox:
				onCheckSubtractionCheckBox(v);
				break;
			case R.id.multiplicationCheckBox:
				onCheckMultiplicationCheckBox(v);
				break;
			case R.id.divisionCheckBox:
				onCheckDivisionCheckBox(v);
				break;
			case R.id.negativeNumbersCheckBox:
				onCheckNegativeNumbersCheckBox(v);
				break;
			default:
				// do nothing.
		}
	}

	protected void onClearStatistics(View view) {
		Toast.makeText(view.getContext(), "inside ProblemSettingsFragment::onClearStatistics()", Toast.LENGTH_LONG).show();
	}

	protected void onOKSettings(View view) {
		Toast.makeText(view.getContext(), "inside ProblemSettingsFragment::onOKSettings()", Toast.LENGTH_LONG).show();
	}

	protected void onCancelSettings(View view) {
		Toast.makeText(view.getContext(), "inside ProblemSettingsFragment::onCancelSettings()", Toast.LENGTH_LONG).show();
	}

	public void onCheckAdditionCheckBox(View view)
	{
		pg.setIncludeAddition(((CheckBox) view).isChecked());
	}

	public void onCheckSubtractionCheckBox(View view)
	{
		pg.setIncludeSubtraction(((CheckBox) view).isChecked());
	}

	public void onCheckMultiplicationCheckBox(View view)
	{
		pg.setIncludeMultiplication(((CheckBox) view).isChecked());
	}

	public void onCheckDivisionCheckBox(View view)
	{
		pg.setIncludeDivision(((CheckBox) view).isChecked());
	}

	public void onCheckNegativeNumbersCheckBox(View view)
	{
		pg.setIncludeNegativeNumbers(((CheckBox) view).isChecked());
	}
}

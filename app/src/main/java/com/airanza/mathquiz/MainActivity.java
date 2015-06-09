package com.airanza.mathquiz;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;

import com.airanza.mathquiz.mathproblems.Problem;
import com.airanza.mathquiz.mathproblems.ProblemGenerator;
import com.example.mathquiz.R;

public class MainActivity extends Activity {
	// for saving activity state in bundle:
	static final String NUMBER_RIGHT = "numberRight";
	static final String NUMBER_WRONG = "numberWrong";
	static final String INCLUDE_ADDITION = "includeAddition";
	static final String INCLUDE_SUBTRACTION = "includeSubtraction";
	static final String INCLUDE_MULTIPLICATION = "includeMultiplication";
	static final String INCLUDE_DIVISION = "includeDivision";
	static final String INCLUDE_NEGATIVE_NUMBERS = "includeNegativeNumbers";
	
	private SharedPreferences prefs = null;
	
	ProblemGenerator pg = new ProblemGenerator();
	Problem p = null;
	
	EditText answerEditText = null;
	TextView feedbackTextView = null;
	TextView problemTextView = null;
	
	TextView rightTextView = null;
	TextView wrongTextView = null;
	
	CheckBox additionCheckBox = null;
	CheckBox subtractionCheckBox = null;
	CheckBox multiplicationCheckBox = null;
	CheckBox divisionCheckBox = null;
	CheckBox negativeNumbersCheckBox = null;
	
	private int numRight = 0;
	private int numWrong = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  // always call the superclass first for view hierarchy

		setContentView(R.layout.activity_main);
		
    		if(savedInstanceState != null)
		{
			// Restore value of members from saved state:
			numRight = savedInstanceState.getInt(NUMBER_RIGHT);
			numWrong = savedInstanceState.getInt(NUMBER_WRONG);
			pg.setIncludeAddition(savedInstanceState.getBoolean(INCLUDE_ADDITION));
			pg.setIncludeSubtraction(savedInstanceState.getBoolean(INCLUDE_SUBTRACTION));
			pg.setIncludeMultiplication(savedInstanceState.getBoolean(INCLUDE_MULTIPLICATION));
			pg.setIncludeDivision(savedInstanceState.getBoolean(INCLUDE_DIVISION));
			pg.setIncludeNegativeNumbers(savedInstanceState.getBoolean(INCLUDE_NEGATIVE_NUMBERS));
		}
		

		setVarsFromSharedPreferences(prefs);
		
		p = pg.generateProblem();
		
		problemTextView = (TextView)findViewById(R.id.problemTextView);
		problemTextView.setText(p.toString());
		
		additionCheckBox = (CheckBox)findViewById(R.id.additionCheckBox);
		additionCheckBox.setChecked(pg.getIncludeAddition());
		
		subtractionCheckBox = (CheckBox)findViewById(R.id.subtractionCheckBox);
		subtractionCheckBox.setChecked(pg.getIncludeSubtraction());
		
		multiplicationCheckBox = (CheckBox)findViewById(R.id.multiplicationCheckBox);
		multiplicationCheckBox.setChecked(pg.getIncludeMultiplication());
		
		divisionCheckBox = (CheckBox)findViewById(R.id.divisionCheckBox);
		divisionCheckBox.setChecked(pg.getIncludeDivision());
		
		negativeNumbersCheckBox = (CheckBox)findViewById(R.id.negativeNumbersCheckBox);
		negativeNumbersCheckBox.setChecked(pg.getIncludeNegativeNumbers());
		
		// *******  Capture the "DONE" button on Android keyboard
    	answerEditText = (EditText)findViewById(R.id.solutionEditText);
    	answerEditText.setOnEditorActionListener(new OnEditorActionListener() {
    		@Override
    		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    			if (actionId == EditorInfo.IME_ACTION_DONE) {
    				onSubmit(v);
    				return(true);
    			}
				return (false);
    		}
    	});
		
		// *******
	}
	
	private void setVarsFromSharedPreferences(SharedPreferences prefs){
		prefs = getPreferences(MODE_PRIVATE);
		pg.setIncludeAddition(prefs.getBoolean(INCLUDE_ADDITION, true));
		pg.setIncludeSubtraction(prefs.getBoolean(INCLUDE_SUBTRACTION, true));
		pg.setIncludeMultiplication(prefs.getBoolean(INCLUDE_MULTIPLICATION, true));
		pg.setIncludeDivision(prefs.getBoolean(INCLUDE_DIVISION, true));
		pg.setIncludeNegativeNumbers(prefs.getBoolean(INCLUDE_NEGATIVE_NUMBERS, true));
		numRight = prefs.getInt(NUMBER_RIGHT, 0);
		numWrong = prefs.getInt(NUMBER_WRONG, 0);
		TextView rightView = (TextView)findViewById(R.id.rightTextView);
		rightView.setText("RIGHT: " + numRight);
		TextView wrongView = (TextView)findViewById(R.id.wrongTextView);
		wrongView.setText("WRONG: " + numWrong);
	}
	
	private void saveVarsToSharedPreferences(SharedPreferences prefs) {
		prefs = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(NUMBER_RIGHT, numRight);
		editor.putInt(NUMBER_WRONG, numWrong);
		editor.putBoolean(INCLUDE_ADDITION, pg.getIncludeAddition());
		editor.putBoolean(INCLUDE_SUBTRACTION, pg.getIncludeSubtraction());
		editor.putBoolean(INCLUDE_MULTIPLICATION, pg.getIncludeMultiplication());
		editor.putBoolean(INCLUDE_DIVISION, pg.getIncludeDivision());
		editor.putBoolean(INCLUDE_NEGATIVE_NUMBERS, pg.getIncludeNegativeNumbers());
		editor.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
			case(R.id.action_show_splash):
				// launch SplashActivity:
				onShowSplash();
				break;
			default:
			// do nothing?
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onSubmit(View view) {
    	answerEditText = (EditText)findViewById(R.id.solutionEditText);
		feedbackTextView = (TextView)findViewById(R.id.feedbackTextView);
		String sAnswer = null;

		try {
			sAnswer = answerEditText.getText().toString();
	    	int answer = (int)Double.parseDouble(sAnswer);
	    	
			if(p.evaluate() == answer) {
				feedbackTextView.setText("YES! [" + answer + "] is correct!");
				answerEditText.setText("");
				feedbackTextView.setBackgroundColor(Color.GREEN);
				
				p = pg.generateProblem();
				
				problemTextView = (TextView)findViewById(R.id.problemTextView);
				problemTextView.setText(p.toString());
				
				numRight++;
				TextView rightView = (TextView)findViewById(R.id.rightTextView);
				rightView.setText("RIGHT: " + numRight);
				
			} else {
				feedbackTextView.setText("SORRY! [" + answer + "] is incorrect!");
				answerEditText.setText("");
				feedbackTextView.setBackgroundColor(Color.RED);
				
				numWrong++;
				TextView wrongView = (TextView)findViewById(R.id.wrongTextView);
				wrongView.setText("WRONG: "+ numWrong);
			}
		} catch (NumberFormatException nfe) {
			feedbackTextView.setText("[" + sAnswer + "] could not be formatted into an integer.");
		}
		
	}
	
	public void onStop() 
	{
		super.onStop();  // always call the superclass method first
		
		// save the right/wrong answers and the state of the ProblemGenerator pg.
		this.saveVarsToSharedPreferences(prefs);
	}
	
	public void onStart()
	{
		super.onStart();  // Always call the superclass method first
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
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) 
	{
		// Save the user's current state
		savedInstanceState.putInt(NUMBER_RIGHT, numRight);
		savedInstanceState.putInt(NUMBER_WRONG, numWrong);
		
		savedInstanceState.putBoolean(INCLUDE_ADDITION, pg.getIncludeAddition());
		savedInstanceState.putBoolean(INCLUDE_SUBTRACTION, pg.getIncludeSubtraction());
		savedInstanceState.putBoolean(INCLUDE_MULTIPLICATION, pg.getIncludeMultiplication());
		savedInstanceState.putBoolean(INCLUDE_DIVISION, pg.getIncludeDivision());
		
		savedInstanceState.putBoolean(INCLUDE_NEGATIVE_NUMBERS, pg.getIncludeNegativeNumbers());
		
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	protected void onShowSplash() {
		// create Intent
		Intent intent = new Intent(this, SplashActivity.class);

		// set Intent such that the SplashActivity can check whether it should be in startup mode or "About" mode
		intent.putExtra(SplashActivity.SPLASH_ACTION, SplashActivity.SPLASH_ABOUT);

		// start the activity (user can cxl with back key).
		startActivity(intent);
	}
}

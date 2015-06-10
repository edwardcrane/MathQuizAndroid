package com.airanza.mathquiz;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;

import com.airanza.mathquiz.mathproblems.Problem;
import com.airanza.mathquiz.mathproblems.ProblemGenerator;

public class MainActivity extends Activity {
	// for saving activity state in bundle:
	static final String NUMBER_RIGHT = "numberRight";
	static final String NUMBER_WRONG = "numberWrong";

	private SharedPreferences prefs = null;
	
	Problem p = null;
	
	EditText answerEditText = null;
	TextView feedbackTextView = null;
	TextView problemTextView = null;
	
	private int numRight = 0;
	private int numWrong = 0;

	private ProblemSettingsFragment problemSettingsFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  // always call the superclass first for view hierarchy

		setContentView(R.layout.activity_main);

		FragmentManager fm = getFragmentManager();
		problemSettingsFragment = (ProblemSettingsFragment) fm.findFragmentById(R.id.problem_settings_fragment);

   		if(savedInstanceState != null)
		{
			// Restore value of members from saved state:
			numRight = savedInstanceState.getInt(NUMBER_RIGHT);
			numWrong = savedInstanceState.getInt(NUMBER_WRONG);
		}

		prefs = getSharedPreferences("mathquiz", MODE_PRIVATE);

		problemSettingsFragment.setVarsFromSharedPreferences(prefs);

		numRight = prefs.getInt(NUMBER_RIGHT, 0);
		numWrong = prefs.getInt(NUMBER_WRONG, 0);
		TextView rightView = (TextView)findViewById(R.id.rightTextView);
		rightView.setText("RIGHT: " + numRight);
		TextView wrongView = (TextView)findViewById(R.id.wrongTextView);
		wrongView.setText("WRONG: " + numWrong);

		p = problemSettingsFragment.getProblemGenerator().generateProblem();
		
		problemTextView = (TextView)findViewById(R.id.problemTextView);
		problemTextView.setText(p.toString());
		
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
				onShowSplash();
				break;
			case(R.id.action_settings):
			default:
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
				
				p = problemSettingsFragment.getProblemGenerator().generateProblem();
				
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
		problemSettingsFragment.saveVarsToSharedPreferences(prefs);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(NUMBER_RIGHT, numRight);
		editor.putInt(NUMBER_WRONG, numWrong);
		editor.commit();
	}
	
	public void onStart()
	{
		super.onStart();  // Always call the superclass method first
	}
	
//	@Override
//	public void onSaveInstanceState(Bundle savedInstanceState)
//	{
////		ProblemGenerator pg = problemSettingsFragment.getProblemGenerator();
//		// Save the user's current state
//		savedInstanceState.putInt(NUMBER_RIGHT, numRight);
//		savedInstanceState.putInt(NUMBER_WRONG, numWrong);
//
////		savedInstanceState.putBoolean(INCLUDE_ADDITION, pg.getIncludeAddition());
////		savedInstanceState.putBoolean(INCLUDE_SUBTRACTION, pg.getIncludeSubtraction());
////		savedInstanceState.putBoolean(INCLUDE_MULTIPLICATION, pg.getIncludeMultiplication());
////		savedInstanceState.putBoolean(INCLUDE_DIVISION, pg.getIncludeDivision());
////
////		savedInstanceState.putBoolean(INCLUDE_NEGATIVE_NUMBERS, pg.getIncludeNegativeNumbers());
//
//		// Always call the superclass so it can save the view hierarchy state
//		super.onSaveInstanceState(savedInstanceState);
//	}

	protected void onShowSplash() {
		// create Intent
		Intent intent = new Intent(this, SplashActivity.class);

		// set Intent such that the SplashActivity can check whether it should be in startup mode or "About" mode
		intent.putExtra(SplashActivity.SPLASH_ACTION, SplashActivity.SPLASH_ABOUT);

		// start the activity (user can cxl with back key).
		startActivity(intent);
	}
}

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;

import com.airanza.mathquiz.mathproblems.Problem;
import com.airanza.mathquiz.mathproblems.ProblemGenerator;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {

    public static final int SETTINGS_REQUEST = 100;
    public static final String MATH_QUIZ_SETTINGS = "mathquizsettings";

	private SharedPreferences prefs = null;
	
	Problem p = null;
	
	EditText answerEditText = null;
	TextView feedbackTextView = null;
	TextView problemTextView = null;
	
	private MathQuizSettings mathQuizSettings = new MathQuizSettings();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  // always call the superclass first for view hierarchy

		setContentView(R.layout.activity_main);

		prefs = getSharedPreferences("mathquiz", MODE_PRIVATE);

        mathQuizSettings.setVarsFromSharedPreferences(prefs);

        updateUIStatistics();
        updateUIProblem();

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

		// Request Ads:
		AdView topBannerAdView = (AdView)findViewById(R.id.main_top_adview);
		AdRequest topBannerAdRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
		topBannerAdView.loadAd(topBannerAdRequest);
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
                onSettings();
                break;
			default:
		}
		return super.onOptionsItemSelected(item);
	}

    private ProblemGenerator getProblemGeneratorFromMathQuizSettings(MathQuizSettings mathQuizSettings) {
        ProblemGenerator pg = new ProblemGenerator();
        pg.setIncludeAddition(mathQuizSettings.isIncludeAddition());
        pg.setIncludeSubtraction(mathQuizSettings.isIncludeSubtraction());
        pg.setIncludeMultiplication(mathQuizSettings.isIncludeMultiplication());
        pg.setIncludeDivision(mathQuizSettings.isIncludeDivision());
        pg.setIncludeNegativeNumbers(mathQuizSettings.isIncludeNegativeNumbers());
        return(pg);
    }

    public void updateUIProblem() {
        p = getProblemGeneratorFromMathQuizSettings(mathQuizSettings).generateProblem();
        TextView problemTextView = (TextView)findViewById(R.id.problemTextView);
        problemTextView.setText(p.toString());
    }

    public void updateUIStatistics() {
        TextView rightView = (TextView)findViewById(R.id.rightTextView);
        rightView.setText("RIGHT: " + mathQuizSettings.getnRight());

        TextView wrongView = (TextView)findViewById(R.id.wrongTextView);
        wrongView.setText("WRONG: "+ mathQuizSettings.getnWrong());
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
				feedbackTextView.setBackgroundColor(Color.GREEN);
                mathQuizSettings.setnRight(mathQuizSettings.getnRight() + 1);
                updateUIProblem();

			} else {
				feedbackTextView.setText("SORRY! [" + answer + "] is incorrect!");
				feedbackTextView.setBackgroundColor(Color.RED);
                mathQuizSettings.setnWrong(mathQuizSettings.getnWrong() + 1);
			}
            ((EditText)findViewById(R.id.solutionEditText)).setText("");
            updateUIStatistics();
		} catch (NumberFormatException nfe) {
			feedbackTextView.setText("[" + sAnswer + "] could not be formatted into an integer.");
		}
	}
	
	public void onStop() 
	{
		super.onStop();  // always call the superclass method first
        mathQuizSettings.saveVarsToSharedPreferences(prefs);
	}
	
	public void onStart()
	{
		super.onStart();  // Always call the superclass method first
	}

    public void onSettings() {
        // create Intent
        Intent intent = new Intent(this, SettingsActivity.class);

        intent.putExtra(MATH_QUIZ_SETTINGS, mathQuizSettings);

        // start the activity (user can cxl with back key).
        startActivityForResult(intent, SETTINGS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if(requestCode == SETTINGS_REQUEST) {
            if(resultCode == RESULT_OK) {
                mathQuizSettings = (MathQuizSettings) data.getSerializableExtra(MATH_QUIZ_SETTINGS);
                TextView rightView = (TextView)findViewById(R.id.rightTextView);
                rightView.setText("RIGHT: " + mathQuizSettings.getnRight());
                TextView wrongView = (TextView)findViewById(R.id.wrongTextView);
                wrongView.setText("WRONG: " + mathQuizSettings.getnWrong());

                ((EditText)findViewById(R.id.solutionEditText)).setText("");
                updateUIStatistics();
                updateUIProblem();
            }
        }
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

package com.airanza.mathquiz;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SettingsActivity extends Activity implements View.OnClickListener {

    private ProblemSettingsFragment problemSettingsFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button submitButton = (Button)findViewById(R.id.settings_submit_button);
        submitButton.setOnClickListener(this);

        Intent intent = getIntent();
        MathQuizSettings settings = (MathQuizSettings)intent.getSerializableExtra("mathquizsettings");

        FragmentManager fm = getFragmentManager();
        problemSettingsFragment = (ProblemSettingsFragment) fm.findFragmentById(R.id.problem_settings_fragment);

        problemSettingsFragment.setMathQuizSettings(settings);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.settings_submit_button:
                onSubmit();
                break;
        }
    }
    private void onSubmit() {
        Intent result = new Intent("com.airanza.mathquiz.MainActivity.SETTINGS_REQUEST", Uri.parse("content://result_uri"));
        result.putExtra(MainActivity.MATH_QUIZ_SETTINGS, problemSettingsFragment.getMathQuizSettings());

        if(getParent() == null) {
            setResult(Activity.RESULT_OK, result);
        } else {
            getParent().setResult(Activity.RESULT_OK, result);
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit) {
            onSubmit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ProblemSettingsFragment getProblemSettingsFragment(){
        return problemSettingsFragment;
    }
}

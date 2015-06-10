package com.airanza.mathquiz;

import android.content.SharedPreferences;


public class MathQuizSettings {
    // Constants for storage/retreival of values from Shared Preferences:
    static final String INCLUDE_ADDITION = "includeAddition";
    static final String INCLUDE_SUBTRACTION = "includeSubtraction";
    static final String INCLUDE_MULTIPLICATION = "includeMultiplication";
    static final String INCLUDE_DIVISION = "includeDivision";
    static final String INCLUDE_NEGATIVE_NUMBERS = "includeNegativeNumbers";
    static final String NUMBER_RIGHT = "numberRight";
    static final String NUMBER_WRONG = "numberWrong";

    private boolean includeAddition = true;
    private boolean includeSubtraction = true;
    private boolean includeMultiplication = true;
    private boolean includeDivision = true;
    private boolean includeNegativeNumbers = true;

    private int nRight = 0;
    private int nWrong = 0;

    public boolean isIncludeAddition() {
        return includeAddition;
    }

    public void setIncludeAddition(boolean includeAddition) {
        this.includeAddition = includeAddition;
    }

    public boolean isIncludeSubtraction() {
        return includeSubtraction;
    }

    public void setIncludeSubtraction(boolean includeSubtraction) {
        this.includeSubtraction = includeSubtraction;
    }

    public boolean isIncludeMultiplication() {
        return includeMultiplication;
    }

    public void setIncludeMultiplication(boolean includeMultiplication) {
        this.includeMultiplication = includeMultiplication;
    }

    public boolean isIncludeDivision() {
        return includeDivision;
    }

    public void setIncludeDivision(boolean includeDivision) {
        this.includeDivision = includeDivision;
    }

    public boolean isIncludeNegativeNumbers() {
        return includeNegativeNumbers;
    }

    public void setIncludeNegativeNumbers(boolean includeNegativeNumbers) {
        this.includeNegativeNumbers = includeNegativeNumbers;
    }

    public int getnRight() {
        return nRight;
    }

    public void setnRight(int nRight) {
        this.nRight = nRight;
    }

    public int getnWrong() {
        return nWrong;
    }

    public void setnWrong(int nWrong) {
        this.nWrong = nWrong;
    }

    public void setVarsFromSharedPreferences(SharedPreferences prefs){
        includeAddition = prefs.getBoolean(INCLUDE_ADDITION, true);
        includeSubtraction = prefs.getBoolean(INCLUDE_SUBTRACTION, true);
        includeMultiplication = prefs.getBoolean(INCLUDE_MULTIPLICATION, true);
        includeDivision = prefs.getBoolean(INCLUDE_DIVISION, true);
        includeNegativeNumbers = prefs.getBoolean(INCLUDE_NEGATIVE_NUMBERS, true);

        nRight = prefs.getInt(NUMBER_RIGHT, 0);
        nWrong = prefs.getInt(NUMBER_WRONG, 0);
    }

    public void saveVarsToSharedPreferences(SharedPreferences prefs) {
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(INCLUDE_ADDITION, includeAddition);
        editor.putBoolean(INCLUDE_SUBTRACTION, includeSubtraction);
        editor.putBoolean(INCLUDE_MULTIPLICATION, includeMultiplication);
        editor.putBoolean(INCLUDE_DIVISION, includeDivision);
        editor.putBoolean(INCLUDE_NEGATIVE_NUMBERS, includeNegativeNumbers);

        editor.putInt(NUMBER_RIGHT, nRight);
        editor.putInt(NUMBER_WRONG, nWrong);

        editor.commit();
    }
}

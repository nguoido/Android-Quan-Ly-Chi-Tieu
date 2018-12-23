package com.vivhp.qlct.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.NumberPicker;

import com.vivhp.qlct.R;

import java.util.Calendar;

/**
 * Created by vivhp on 11/20/2016.
 */

public class YearPicker {

    private static final int MIN_YEAR = 1970;

    private static final int MAX_YEAR = 2099;

    private View view;
    private Activity activity;
    private AlertDialog.Builder builder;
    private AlertDialog pickerDialog;
    private boolean build = false;
    private NumberPicker yearNumberPicker;

    /**
     * Khởi tạo mới month year picker.
     *
     * @param activity truyền vào activity
     */

    public YearPicker(Activity activity) {
        this.activity = activity;
        this.view = activity.getLayoutInflater().inflate(R.layout.year_picker, null);
    }

    /**
     * Tạo year alert dialog.
     *
     * @param negativeButtonListener
     * @param positiveButtonListener
     */

    public void build(DialogInterface.OnClickListener negativeButtonListener, DialogInterface.OnClickListener positiveButtonListener) {
        this.build(-1, -1, positiveButtonListener, negativeButtonListener);
    }

    private int currentYear;

    /**
     * Tạo dialog year picker;
     *
     * @param positiveButtonListener Button trái
     * @param negativeButtonListener
     */

    public void build(int selectedYear, int i, DialogInterface.OnClickListener positiveButtonListener,
                      DialogInterface.OnClickListener negativeButtonListener) {

        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);

        //Check exception

        if (selectedYear < MIN_YEAR || selectedYear > MAX_YEAR) {
            selectedYear = currentYear;
        }

        if (selectedYear == -1) {
            selectedYear = currentYear;
        }

        //Build dialog
        builder = new AlertDialog.Builder(activity);
        builder.setView(view);

        yearNumberPicker = (NumberPicker) view.findViewById(R.id.yearNumberPicker);
        yearNumberPicker.setMinValue(MIN_YEAR);
        yearNumberPicker.setMaxValue(MAX_YEAR);
        yearNumberPicker.setValue(selectedYear);

        yearNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        builder.setPositiveButton(activity.getString(R.string.positive_button_text), positiveButtonListener);
        builder.setNegativeButton(activity.getString(R.string.negative_button_text), negativeButtonListener);
        build = true;
        pickerDialog = builder.create();
    }

    /**
     * Show year picker dialog.
     */

    public void show() {
        if (build) {
            pickerDialog.show();
        } else
            throw new IllegalArgumentException("Build picker before use");
    }

    /**
     * Gets the selected year.
     * <p>
     * return the selected year
     */
    public int getSelectedYear() {
        return yearNumberPicker.getValue();
    }

    /**
     * Gets the current year.
     * <p>
     * return the current year
     */
    public int getCurrentYear() {
        return currentYear;
    }

    /**
     * Sets the year value changed listener.
     * <p>
     * param valueChangeListener
     * the new year value changed listener
     */
    public void setYearValueChangedListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        yearNumberPicker.setOnValueChangedListener(valueChangeListener);
    }

    /**
     * Sets the year wrap selector wheel.
     * <p>
     * param wrapSelectorWheel
     * the new year wrap selector wheel
     */
    public void setYearWrapSelectorWheel(boolean wrapSelectorWheel) {
        yearNumberPicker.setWrapSelectorWheel(wrapSelectorWheel);
    }
}

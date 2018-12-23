package com.vivhp.qlct;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.StringTokenizer;

/**
 * Created by KidTjTjLuV on 22/10/2016.
 */

public class HienDoiTien implements TextWatcher {

    TextView textView;


    public HienDoiTien(TextView textView) {
        this.textView = textView;


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            textView.removeTextChangedListener(this);
            String value = textView.getText().toString();

            if (value != null && !value.equals("")) {

                if (value.startsWith(".")) {
                    textView.setText("0.");
                }
                if (value.startsWith("0") && !value.startsWith("0.")) {
                    textView.setText("");

                }

                String str = textView.getText().toString().replaceAll("\\.", "");
                if (!value.equals(""))
                    textView.setText(getDecimalFormattedString(str));
                textView.setText(textView.getText().toString().length());
            }
            textView.addTextChangedListener(this);
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
            textView.addTextChangedListener(this);
        }
    }

    public static String getDecimalFormattedString(String value) {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1) {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt(-1 + str1.length()) == '.') {
            j--;
            str3 = ".";
        }
        for (int k = j; ; k--) {
            if (k < 0) {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3) {
                str3 = "." + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            str3 = str3.replace("-.", "-");

            i++;
        }

    }

    public static String trimCommaOfString(String string) {
        if (string.contains(",")) {
            string = string.replace("-.", "-");
            return string.replace(",", "");
        } else {
            return string.replace("-.", "-");
        }
    }
}

package com.vivhp.qlct.dialog;

import android.app.ProgressDialog;
import android.content.Context;

import com.vivhp.qlct.R;

/**
 * Created by vivhp on 11/19/2016.
 */

public class DialogProgressBar {
    private ProgressDialog progressDialog;

    public DialogProgressBar(Context context, boolean isTouchOutside, boolean isCancel, String title, String message) {
        progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setCanceledOnTouchOutside(isTouchOutside);
        progressDialog.setCancelable(isCancel);

        if (title != null)
            progressDialog.setTitle(title);

        if (message != null)
            progressDialog.setMessage(message);
    }

    public void hideProgressBar() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public void showProgressBar() {
        if (progressDialog != null)
            progressDialog.show();
    }
}
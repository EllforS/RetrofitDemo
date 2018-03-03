package com.ellfors.dagger2.http.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.ellfors.dagger2.http.config.RetrofitConfig;


/**
 * 模拟ProgressDialog
 */
public class ProgressDialogHandler extends Handler
{

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private CustomProgressDialog mDialog;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener, boolean cancelable)
    {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog()
    {
        if (mDialog == null)
        {
            mDialog = new CustomProgressDialog(context);
            mDialog.setTipText(RetrofitConfig.PROGRESS_DIALOG_MESSAGE);
            mDialog.setCancelable(cancelable);
            if (cancelable)
            {
                mDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialogInterface)
                    {
                        mProgressCancelListener.onProgressCancel();
                    }
                });
            }
            if (!mDialog.isShowing())
            {
                mDialog.show();
            }
        }
    }

    private void dismissProgressDialog()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg)
    {
        switch (msg.what)
        {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
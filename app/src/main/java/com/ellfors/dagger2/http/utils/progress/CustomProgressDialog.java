package com.ellfors.dagger2.http.utils.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.ellfors.dagger2.R;

/**
 * 自定义ProgressDialog
 */
public class CustomProgressDialog extends ProgressDialog
{
    TextView popText;
    private String tipText;

    public CustomProgressDialog(Context context, int theme)
    {
        super(context, theme);
    }

    public CustomProgressDialog(Context context)
    {
        this(context, R.style.transparent_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        popText = (TextView) findViewById(R.id.dialog_text);
        if (null != tipText)
        {
            this.popText.setText(tipText);
        }
    }

    public void setTipText(int contentResId)
    {
        if (null != this.popText)
        {
            this.popText.setText(contentResId);
        }
        else
        {
            this.tipText = getContext().getString(contentResId);
        }
    }

    public void setTipText(String tip)
    {
        if (null != this.popText)
        {
            this.popText.setText(tip);
        }
        else
        {
            this.tipText = tip;
        }
    }
}
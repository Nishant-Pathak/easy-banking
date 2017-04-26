package com.drawers.banklib.otpdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public abstract class BaseDialog extends Dialog implements DialogInterface {

    private View mDialogView;

    public BaseDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    @SuppressWarnings("ConstantConditions")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
    }

    private void init(final Context context) {
        mDialogView = inflateDialogView(context);
        extractElements();
        setContentView(mDialogView);
    }

    /**
     * Method to inflate.
     * @param context activity context to inflate views.
     * @return The inflated view.
     */
    abstract View inflateDialogView(Context context);

    abstract void extractElements();

    /**
     * Provides smart casting to extract views.
     * @param identifier the identifier to extract.
     * @param <T> the type for cast.
     * @return the extracted view.
     */
    @SuppressWarnings("unchecked")
    public <T> T extractView(@IdRes int identifier) {
        return (T) mDialogView.findViewById(identifier);
    }
}

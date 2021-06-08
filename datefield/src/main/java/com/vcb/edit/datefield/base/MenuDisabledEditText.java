package com.vcb.edit.datefield.base;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * The class that will disable the copy/ paste on the edit text.
 * This class can be extended or used as raw.
 */
public class MenuDisabledEditText extends AppCompatEditText {
    public MenuDisabledEditText(@NonNull Context context) {
        super(context);
        initialize();
    }

    public MenuDisabledEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MenuDisabledEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @Override
    public void setTextIsSelectable(boolean selectable) {
        super.setTextIsSelectable(false);
//        super.setTextIsSelectable(selectable);
    }

    @Override
    public boolean isSuggestionsEnabled() {
        return false;
//        return super.isSuggestionsEnabled();
    }

    boolean canPaste() {
        return false;
    }

    @Override
    public int getSelectionStart() {
        try {
            for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
                if(null != element && element.getMethodName().equals("canPaste")) {
                    return -1;
                }
            }
        } catch (Exception ex) {
        }
        return super.getSelectionStart();
    }

    /**
     * Can disable from pasting if user tap on the menu.
     * @param id the id of the menu
     * @return int - handled or not
     */
    @Override
    public boolean onTextContextMenuItem(int id) {
        switch (id) {
            case android.R.id.paste:
            case android.R.id.pasteAsPlainText:
                return false;

        }
        return super.onTextContextMenuItem(id);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null != event && MotionEvent.ACTION_DOWN == event.getAction()) {
            disableInsertion();
        }
        return super.onTouchEvent(event);
    }

    private void disableInsertion() {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(this);

            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

//            Field mSelectionControllerEnabled = editorClass.getDeclaredField("mSelectionControllerEnabled");
//            mSelectionControllerEnabled.setAccessible(true);
//            mSelectionControllerEnabled.set(editorObject, false);
        } catch (Exception ex) {
        }
    }

    private void initialize() {
        disableCopyPaste();
    }

    private void disableCopyPaste() {
        clearMenu();
        disableLongLick();
        disableTextSelectable();
    }

    private void disableLongLick() {
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        setLongClickable(false);
    }

    private void disableTextSelectable() {
        setTextIsSelectable(false);
    }

    private void clearMenu() {
        try {
            setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    if(null != menu) {
                        menu.clear();
                    }
                }
            });
            setCustomSelectionActionModeCallback(actionModeCallback);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setCustomInsertionActionModeCallback(actionModeCallback);
            }
        } catch (Exception ex) {
        }
    }

    ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            try {
                if(null != menu) {
                    menu.removeItem(android.R.id.paste);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        menu.removeItem(android.R.id.pasteAsPlainText);
                    }
                }
                return true;
            } catch (Exception ex) {
            }
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return true;
            try {
                for(int i =0;i<menu.size(); i++) {
                    if(menu.getItem(i).getTitle().toString().equals("Clipboard")
                            || menu.getItem(i).getTitle().toString().equals("Paste")) {
                        menu.getItem(i).setVisible(false);
                    }
                }
            } catch (Exception ex) {
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    };
}

package com.example.maestro.orientation;

import android.app.Activity;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Display a human readable value of the current user_orientation system settings.
 * 0 -> "PORTRAIT"
 * 1 -> "LANDSCAPE_LEFT"
 * 2 -> "UPSIDE_DOWN"
 * 3 -> "LANDSCAPE_RIGHT"
 * A content observer is used because in some cases the same activity
 * is "simply" rotated without being even paused and resumed. For example,
 * changing the user_orientation from "PORTRAIT" to "UPSIDE_DOWN" keeps the
 * same activity (no onDestroy and onCreate) and does not trigger onPaused/onResumed.
 */
public class MainActivity extends Activity {

    private TextView textView;
    private ContentObserver contentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textView = new TextView(this);
        textView.setGravity(Gravity.CENTER);
        setContentView(
                textView,
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        Handler handler = new Handler(Looper.getMainLooper());
        contentObserver = new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                updateOrientationLabel();
            }
        };

        getContentResolver().registerContentObserver(
                Settings.System.getUriFor(Settings.System.USER_ROTATION),
                true,
                contentObserver
        );

        updateOrientationLabel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    private void updateOrientationLabel() {
        int userOrientation = Settings.System.getInt(
                getContentResolver(),
                Settings.System.USER_ROTATION,
                0
        );

        String orientationLabel;
        switch (userOrientation) {
            case 0:
                orientationLabel = "PORTRAIT";
                break;
            case 1:
                orientationLabel = "LANDSCAPE_LEFT";
                break;
            case 2:
                orientationLabel = "UPSIDE_DOWN";
                break;
            case 3:
                orientationLabel = "LANDSCAPE_RIGHT";
                break;
            default:
                throw new IllegalStateException(
                        "Unsupported user orientation. Found: " + userOrientation
                );
        }

        textView.setText(orientationLabel);
    }
}


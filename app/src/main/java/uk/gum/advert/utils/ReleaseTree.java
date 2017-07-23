package uk.gum.advert.utils;


import android.util.Log;

import timber.log.Timber;

public class ReleaseTree extends Timber.Tree {

    private static final int MAX_LOG_LENGTH = 3000;

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (isLoggable(tag, priority)) {
            if (message.length() < MAX_LOG_LENGTH) {
                logShortMessage(priority, tag, message);
            } else {
                logLongMessage(priority, tag, message);
            }
        }
    }

    @Override
    protected boolean isLoggable(String tag, int priority) {
        //Log everything else except these
        return !(priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO);
    }

    private void logShortMessage(int priority, String tag, String message) {
        if (priority == Log.ASSERT) {
            Log.wtf(tag, message);
        } else {
            Log.println(priority, tag, message);
        }
    }

    private void logLongMessage(int priority, String tag, String message) {
        for (int i = 0, length = message.length(); i < length; i++) {
            int newLine = message.indexOf('\n', i);
            newLine = newLine != -1 ? newLine : length;
            do {
                final int end = Math.min(newLine, i + MAX_LOG_LENGTH);
                final String part = message.substring(i, end);
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part);
                } else {
                    Log.println(priority, tag, part);
                }
            } while (i < newLine);
        }
    }
}

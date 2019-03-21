package com.kimboofactory.iconvert.debug;

import android.app.Application;
import android.util.Log;

import com.aleengo.peach.toolbox.commons.common.PeachConfig;
import com.squareup.leakcanary.AnalysisResult;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.HeapDump;
import com.squareup.leakcanary.LeakCanary;

import androidx.annotation.NonNull;

/**
 * Created by CK_ALEENGO on 21/03/2019.
 * Copyright (c) 2019. All rights reserved.
 */
public final class LeakLoggerService extends DisplayLeakService {

    private static final String TAG = "LeakCanaryLoggerService";

    public LeakLoggerService() {
    }

    public static void installLeakCanary(Application application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.refWatcher(application)
                .listenerServiceClass(LeakLoggerService.class)
                .buildAndInstall();
    }

    @Override
    protected void afterDefaultHandling(@NonNull HeapDump heapDump, @NonNull AnalysisResult result, @NonNull String leakInfo) {
        if (!result.leakFound || result.excludedLeak) {
            return;
        }

        if (PeachConfig.isDebug()) {
            Log.w(TAG, leakInfo);
        }
    }
}

/*
 * Created by Ubique Innovation AG
 * https://www.ubique.ch
 * Copyright (c) 2020. All rights reserved.
 */

package org.dpppt.android.sdk;

import android.content.Context;

import java.io.OutputStream;

import org.dpppt.android.sdk.internal.AppConfigManager;
import org.dpppt.android.sdk.internal.crypto.CryptoDatabaseHelper;
import org.dpppt.android.sdk.internal.database.Database;
import org.dpppt.android.sdk.internal.logger.LogDatabaseHelper;
import org.dpppt.android.sdk.util.DeviceHelper;

public class DP3TCalibrationHelper {

	public static void setCalibrationTestDeviceName(Context context, String name) {
		AppConfigManager.getInstance(context).setCalibrationTestDeviceName(name);
	}

	public static String getCalibrationTestDeviceName(Context context) {
		return AppConfigManager.getInstance(context).getCalibrationTestDeviceName();
	}

	public static void disableCalibrationTestDeviceName(Context context) {
		AppConfigManager.getInstance(context).setCalibrationTestDeviceName(null);
	}

	public static void exportDb(Context context, OutputStream targetOut, Runnable onExportedListener) {
		new Thread(() -> {
			CryptoDatabaseHelper.copySKsToDatabase(context);
			LogDatabaseHelper.copyLogDatabase(context);
			DeviceHelper.addDeviceInfoToDatabase(context);
			Database db = new Database(context);
			db.exportTo(context, targetOut, response -> onExportedListener.run());
		}).start();
	}

	public static void start(Context context, boolean advertise, boolean receive) {
		DP3T.start(context, advertise, receive);
	}

}

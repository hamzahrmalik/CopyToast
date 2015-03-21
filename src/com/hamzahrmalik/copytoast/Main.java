package com.hamzahrmalik.copytoast;

import java.lang.reflect.Field;

import android.content.ClipData;
import android.content.Context;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Main implements IXposedHookLoadPackage {

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		final Class<?> clazz = XposedHelpers.findClass(
				"android.content.ClipboardManager", lpparam.classLoader);
		XposedHelpers.findAndHookMethod(clazz, "setPrimaryClip",
				ClipData.class, new XC_MethodHook() {
					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						Field contextf = XposedHelpers.findField(clazz,
								"mContext");

						Context c = (Context) contextf.get(param.thisObject);
						XposedBridge.log("COPIEEED");

						Toast.makeText(c, "Text copied", Toast.LENGTH_SHORT)
								.show();

					}
				});
	}

}
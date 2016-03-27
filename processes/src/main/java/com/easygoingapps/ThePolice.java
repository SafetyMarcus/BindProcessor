package com.easygoingapps;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;

/**
 * @author Marcus Hooper
 */
public class ThePolice
{
	private static final String LOG_TAG = "ThePolice";

	public static void watch(Activity activity)
	{
		try
		{
			Class<?> bindingClass = Class.forName(activity.getClass().getName() + "Binding");
			Method method = bindingClass.getMethod("watch", Activity.class);
			method.invoke(bindingClass, activity);
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, "Exception finding watch method for " + activity.getClass().getName());
		}
	}

	public static void watch(View layout, Fragment fragment)
	{
		try
		{
			Class<?> bindingClass = Class.forName(fragment.getClass().getName() + "Binding");
			Method method = bindingClass.getMethod("watch", View.class, Fragment.class);
			method.invoke(bindingClass, layout, fragment);
		}
		catch(Exception e)
		{
			Log.e(LOG_TAG, "Exception finding watch method for " + fragment.getClass().getName());
		}
	}
}
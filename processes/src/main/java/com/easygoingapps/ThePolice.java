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
		String activityName = activity.getClass().getName();
		try
		{
			invokeMethod(Class.forName(activityName + "Binding"), activity);
		}
		catch(Exception e)
		{
			Log.i(LOG_TAG, "Exception binding for activity. Checking super class");
			Class superClass = activity.getClass().getSuperclass();
			if(superClass != null)
			{
				try
				{
					invokeMethod(Class.forName(superClass.getName() + "Binding"), activity);
				}
				catch(Exception e1)
				{
					Log.e(LOG_TAG, "Exception Binding " + activityName);
				}
			}
			else
				Log.e(LOG_TAG, "Exception Binding " + activityName);
		}
	}

	private static void invokeMethod(Class bindingClass, Activity activity) throws Exception
	{
		if(bindingClass == null) //Throwing to break into catch clause
			throw new ClassNotFoundException();

		Method method = bindingClass.getMethod("watch", Activity.class);
		method.invoke(bindingClass, activity);
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
package com.easygoingapps.generators;

import com.easygoingapps.utils.BindState;

import java.util.ArrayList;

/**
 * @author Marcus Hooper
 */
public class ThePoliceGenerator extends SourceGenerator
{
	public static final String NAME = "ThePolice";
	private BindState firstState;

	public ThePoliceGenerator(BindState state)
	{
		super(NAME, false);
		this.firstState = state;
	}

	@Override
	public String getPackage()
	{
		return "package " + firstState.packageName + ";";
	}

	@Override
	public String getImports()
	{
		ImportBuilder imports = new ImportBuilder()
				.appendView()
				.append("import android.app.Activity;\n")
				.append("import android.app.Fragment;\n")
				.append("import android.util.Log;\n")
				.append("import java.lang.reflect.Method;\n\n");

		return imports.build();
	}

	@Override
	public String getVariables()
	{
		return "\tprivate static final String LOG_TAG = \"ThePolice\";\n\n";
	}

	@Override
	public String getBody()
	{
		return new CodeBuilder()
				.indent(1).append("public static void watch(Activity activity)\n")
				.indent(1).append("{\n")
				.indent(2).append("try\n")
				.indent(2).append("{\n")
				.indent(3).append("Class<?> bindingClass = Class.forName(activity.getClass().getName() + \"Binding\");\n")
				.indent(3).append("Method method = bindingClass.getMethod(\"watch\", Activity.class);\n")
				.indent(3).append("method.invoke(bindingClass, activity);\n")
				.indent(2).append("}\n")
				.indent(2).append("catch(Exception e)\n")
				.indent(2).append("{\n")
				.indent(3).append("Log.e(LOG_TAG, \"Exception finding watch method for \" + activity.getClass().getName());\n")
				.indent(2).append("}\n")
				.indent(1).append("}\n\n")
				.indent(1).append("public static void watch(View layout, Fragment fragment)\n")
				.indent(1).append("{\n")
				.indent(2).append("try\n")
				.indent(2).append("{\n")
				.indent(3).append("Class<?> bindingClass = Class.forName(fragment.getClass().getName() + \"Binding\");\n")
				.indent(3).append("Method method = bindingClass.getMethod(\"watch\", View.class, Fragment.class);\n")
				.indent(3).append("method.invoke(bindingClass, layout, fragment);\n")
				.indent(2).append("}\n")
				.indent(2).append("catch(Exception e)\n")
				.indent(2).append("{\n")
				.indent(3).append("Log.e(LOG_TAG, \"Exception finding watch method for \" + fragment.getClass().getName());\n")
				.indent(2).append("}\n")
				.indent(1).append("}")
				.toString();
	}
}

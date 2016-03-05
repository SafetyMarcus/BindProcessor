package com.easygoingapps.generators;

import com.easygoingapps.utils.BindState;

import java.util.ArrayList;

/**
 * @author Marcus Hooper
 */
public class ThePoliceGenerator extends SourceGenerator
{
	public static final String NAME = "ThePolice";
	private ArrayList<BindState> states;

	public ThePoliceGenerator(ArrayList<BindState> states)
	{
		super(NAME, false);
		this.states = states;
	}

	@Override
	public String getPackage()
	{
		return "package " + states.get(0).packageName + ";";
	}

	@Override
	public String getImports()
	{
		ImportBuilder imports = new ImportBuilder()
				.appendView()
				.append("import android.app.Activity;")
				.append("import android.app.Fragment;");

		return imports.build();
	}

	@Override
	public String getVariables()
	{
		return "";
	}

	@Override
	public String getBody()
	{
		return new CodeBuilder()
				.indent(1).append("public static void watch(Activity activity)\n")
				.indent(1).append("{\n")
				.append(addActivityMappings())
				.indent(1).append("}\n\n")
				.indent(1).append("public static void watch(View layout, Fragment fragment)\n")
				.indent(1).append("{\n")
				.append(addFragmentMappings())
				.indent(1).append("}\n")
				.toString();
	}

	private String addActivityMappings()
	{
		CodeBuilder mappings = new CodeBuilder();
		int added = 0;

		for(BindState state : states)
		{
			if(state.className.contains("Fragment"))
				continue;

			mappings.indent(2);

			if(added > 0)
				mappings.append("else ");

			mappings.append("if(activity instanceof ").append(state.className).append(")\n")
					.indent(3).append(state.className).append("Binding.watch(activity);\n");

			added++;
		}

		return mappings.toString();
	}

	private String addFragmentMappings()
	{
		CodeBuilder mappings = new CodeBuilder();
		int added = 0;

		for(BindState state : states)
		{
			if(state.className.contains("Activity"))
				continue;

			mappings.indent(2);

			if(added > 0)
				mappings.append("else ");

			mappings.append("if(fragment instanceof ").append(state.className).append(")\n")
					.indent(3).append(state.className).append("Binding.watch(layout, fragment);\n");

			added++;
		}

		return mappings.toString();
	}
}

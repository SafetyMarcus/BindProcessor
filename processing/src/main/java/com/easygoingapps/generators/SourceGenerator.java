package com.easygoingapps.generators;

import java.util.ArrayList;

/**
 * @author Marcus Hooper
 */
public abstract class SourceGenerator
{
	protected static final String INDENT = "    ";
	private static final String PREFIX = "au.com.easygoingapps.thepolice.observers.";

	public String className;
	protected ArrayList<SourceVariable> variables = new ArrayList<>();

	public SourceGenerator(String className)
	{
		this.className = className;
	}

	public String getPackage()
	{
		return PREFIX + className + ';';
	}

	public abstract String getImports();

	public abstract String getVariables();

	public String getClassName()
	{
		return "public " + className + "\n{\n";
	}

	private String getConstructor()
	{
		StringBuilder constructor = new StringBuilder("public " + className + "(");

		for(int i = 0, size = variables.size(); i < size; i++)
		{
			SourceVariable variable = variables.get(i);
			constructor.append(variable.type).append(' ').append(variable.name);

			if(i < size - 1)
				constructor.append(", ");
		}

		constructor.append("\n{\n");

		for(int i = 0, size = variables.size(); i < size; i++)
		{
			SourceVariable variable = variables.get(i);
			constructor.append("this.").append(variable.name).append(" = ").append(variable.name).append(";\n");
		}

		return constructor.append("\n}\n\n").toString();
	}

	public abstract String getBody();

	public String generate()
	{
		return getPackage() + "\n\n"
				+ getClassName()
				+ getImports()
				+ getVariables()
				+ getConstructor()
				+ getBody() + "}";
	}
}

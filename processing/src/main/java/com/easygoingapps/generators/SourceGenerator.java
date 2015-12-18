package com.easygoingapps.generators;

import java.util.ArrayList;

/**
 * @author Marcus Hooper
 */
public abstract class SourceGenerator
{
	public static final String INDENT = "    ";
	private static final String PREFIX = "au.com.easygoingapps.thepolice.observers";

	public String className;
	public boolean hasConstructor;
	protected ArrayList<SourceVariable> variables = new ArrayList<>();

	public SourceGenerator(String className)
	{
		this.className = className;
		this.hasConstructor = true;
	}

	public SourceGenerator(String className, boolean hasConstructor)
	{
		this.className = className;
		this.hasConstructor = hasConstructor;
	}

	public String getPackage()
	{
		return "package " + PREFIX + ';';
	}

	public abstract String getImports();

	public abstract String getVariables();

	public String getInterfaces()
	{
		return "";
	}

	public String getClassName()
	{
		StringBuilder builder = new StringBuilder("public class " + className);

		String interfaces = getInterfaces();
		if(interfaces.length() > 0)
			builder.append(" implements ").append(interfaces);

		return builder.append("\n{\n").toString();
	}

	private String getConstructor()
	{
		if(!hasConstructor)
			return "";

		StringBuilder constructor = new StringBuilder(INDENT + "public " + className + "(");

		for(int i = 0, size = variables.size(); i < size; i++)
		{
			SourceVariable variable = variables.get(i);
			constructor.append(variable.type).append(' ').append(variable.name);

			if(i < size - 1)
				constructor.append(", ");
		}

		constructor.append(")\n").append(INDENT).append("{\n");

		for(int i = 0, size = variables.size(); i < size; i++)
		{
			SourceVariable variable = variables.get(i);
			constructor.append(INDENT).append(INDENT).append("this.").append(variable.name).append(" = ").append(variable.name).append(";\n");
		}

		return constructor.append(INDENT).append("}\n\n").toString();
	}

	public abstract String getBody();

	public String generate()
	{
		return getPackage() + "\n\n"
				+ getImports()
				+ getClassName()
				+ getVariables()
				+ getConstructor()
				+ getBody() + "\n}";
	}
}

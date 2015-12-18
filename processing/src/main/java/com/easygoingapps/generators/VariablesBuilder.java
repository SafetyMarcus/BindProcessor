package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class VariablesBuilder extends SourceBuilder
{
	CodeBuilder variables = new CodeBuilder();

	public VariablesBuilder appendVariable(SourceVariable variable, boolean doubleSpace)
	{
		variables.indent(1).append(variable.access).append(" ")
				.append(variable.type).append(" ").append(variable.name).append(";\n");

		if(doubleSpace)
			variables.append("\n");

		return this;
	}

	public VariablesBuilder appendVariable(SourceVariable variable)
	{
		return appendVariable(variable, false);
	}

	@Override
	public String build()
	{
		return variables.append("\n").toString();
	}
}

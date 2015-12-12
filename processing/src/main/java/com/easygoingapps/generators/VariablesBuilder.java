package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class VariablesBuilder extends SourceBuilder
{
	StringBuilder variables = new StringBuilder();

	public VariablesBuilder appendVariable(SourceVariable variable, boolean doubleSpace)
	{
		variables.append(variable.access).append(' ').append(variable.name).append(";\n");

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

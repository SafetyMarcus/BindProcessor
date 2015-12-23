package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class CodeBuilder
{
	private static final String INDENT = "\t";

	private final StringBuilder builder = new StringBuilder();

	public CodeBuilder indent(int level)
	{
		while(level-- > 0)
			builder.append(INDENT);

		return this;
	}

	public CodeBuilder appendOverride()
	{
		builder.append("@Override\n");
		return this;
	}

	public CodeBuilder append(String appendage)
	{
		builder.append(appendage);
		return this;
	}

	@Override
	public String toString()
	{
		return builder.toString();
	}
}

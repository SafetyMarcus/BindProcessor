package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
abstract class SourceBuilder
{
	public abstract String build();

	@Override
	public String toString()
	{
		return build();
	}
}

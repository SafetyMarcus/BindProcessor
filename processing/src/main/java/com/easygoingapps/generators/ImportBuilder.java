package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
class ImportBuilder extends SourceBuilder
{
	private static final String ANDROID_WIDGET = "android.widget";
	private static final String LOCAL_UTIL = "com.easygoingapps.utils";

	private final StringBuilder builder = new StringBuilder();

	private ImportBuilder appendWidget(String importName)
	{
		builder.append(ANDROID_WIDGET).append(importName).append(";\n");
		return this;
	}

	private ImportBuilder appendLocalUtil(String importName)
	{
		builder.append(LOCAL_UTIL).append(importName).append(";\n");
		return this;
	}

	public ImportBuilder appendCheckbox()
	{
		return appendWidget("CheckBox");
	}

	public ImportBuilder appendCompoundButton()
	{
		return appendWidget("CompoundButton");
	}

	public ImportBuilder appendObserver()
	{
		return appendLocalUtil("Observer");
	}

	public ImportBuilder appendState()
	{
		return appendLocalUtil("State");
	}

	@Override
	public String build()
	{
		return builder.append("\n").toString();
	}
}

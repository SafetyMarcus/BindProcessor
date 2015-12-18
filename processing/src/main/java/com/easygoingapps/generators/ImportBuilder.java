package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
class ImportBuilder extends SourceBuilder
{
	private static final String ANDROID_WIDGET = "import android.widget.";
	private static final String ANDROID_TEXT = "import android.text.";
	private static final String ANDROID_DRAWABLE = "import android.graphics.drawable.";
	private static final String LOCAL_UTIL = "import com.easygoingapps.utils.";
	private static final String OBSERVER = "import au.com.easygoingapps.thepolice.observers.";

	private final StringBuilder builder = new StringBuilder();

	public ImportBuilder append(String appendage)
	{
		builder.append(appendage).append("\n");
		return this;
	}

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

	public ImportBuilder appendObserverClass(String observer)
	{
		builder.append(OBSERVER).append(observer).append(";\n");
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

	public ImportBuilder appendEditText()
	{
		return appendWidget("EditText");
	}

	public ImportBuilder appendImageView()
	{
		return appendWidget("ImageView");
	}

	public ImportBuilder appendTextView()
	{
		return appendWidget("TextView");
	}

	public ImportBuilder appendView()
	{
		return append("import android.view.View;");
	}

	public ImportBuilder appendObserver()
	{
		return appendLocalUtil("Observer");
	}

	public ImportBuilder appendState()
	{
		return appendLocalUtil("State");
	}

	public ImportBuilder appendText(String importName)
	{
		builder.append(ANDROID_TEXT).append(importName).append(";\n");
		return this;
	}

	public ImportBuilder appendDrawable(String importName)
	{
		builder.append(ANDROID_DRAWABLE).append(importName).append(";\n");
		return this;
	}

	@Override
	public String build()
	{
		return builder.append("\n").toString();
	}
}

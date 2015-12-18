package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class TextViewObserverGenerator extends SourceGenerator
{
	public static final String NAME = "TextViewObserver";

	public TextViewObserverGenerator()
	{
		super(NAME);
	}

	@Override
	public String getInterfaces()
	{
		return "Observer<Object>";
	}

	@Override
	public String getImports()
	{
		return new ImportBuilder()
				.appendDrawable("ColorDrawable")
				.appendTextView()
				.appendObserver().build();
	}

	@Override
	public String getVariables()
	{
		VariablesBuilder builder = new VariablesBuilder();
		SourceVariable textView = new SourceVariable("private", "TextView", "textView");
		variables.add(textView);
		builder.appendVariable(textView);

		return builder.build();
	}

	@Override
	public String getBody()
	{
		return new CodeBuilder().indent(1).appendOverride()
				.indent(1).append("public void onChange(Object value)\n")
				.indent(1).append("{\n")
				.indent(2).append("textView.setText(value.toString());\n")
				.indent(1).append("}\n").toString();
	}
}

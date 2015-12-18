package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class EditTextBinderGenerator extends SourceGenerator
{
	private static final String NAME = "EditTextBinder";

	public EditTextBinderGenerator()
	{
		super(NAME);
	}

	@Override
	public String getImports()
	{
		return new ImportBuilder().appendText("Editable")
				.appendText("TextWatcher")
				.appendEditText()
				.append("\n")
				.appendObserver()
				.appendState()
				.build();
	}

	@Override
	public String getInterfaces()
	{
		return "TextWatcher";
	}

	@Override
	public String getVariables()
	{
		VariablesBuilder builder = new VariablesBuilder();

		SourceVariable value = new SourceVariable("private", "State<String>", "value");
		variables.add(value);
		builder.appendVariable(value);

		return builder.build();
	}

	@Override
	public String getBody()
	{
		StringBuilder body = new StringBuilder();

		body.append(INDENT).append("@Override\n")
				.append(INDENT).append("public void beforeTextChanged(CharSequence s, int start, int count, int after)\n")
				.append(INDENT).append("{\n").append(INDENT).append("}\n\n")
				.append(INDENT).append("@Override\n")
				.append(INDENT).append("public void onTextChanged(CharSequence s, int start, int before, int count)\n")
				.append(INDENT).append("{\n").append(INDENT).append("}\n\n")
				.append(INDENT).append("@Override\n")
				.append(INDENT).append("public void afterTextChanged(Editable s)\n")
				.append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append("value.setValue(s.toString());\n")
				.append(INDENT).append("}");

		return body.toString();
	}
}

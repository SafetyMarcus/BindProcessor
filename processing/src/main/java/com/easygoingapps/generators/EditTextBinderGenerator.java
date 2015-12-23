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
		CodeBuilder body = new CodeBuilder();

		body.indent(1).appendOverride()
				.indent(1).append("public void beforeTextChanged(CharSequence s, int start, int count, int after)\n")
				.indent(1).append("{\n").indent(1).append("}\n\n")
				.indent(1).append("@Override\n")
				.indent(1).append("public void onTextChanged(CharSequence s, int start, int before, int count)\n")
				.indent(1).append("{\n").indent(1).append("}\n\n")
				.indent(1).append("@Override\n")
				.indent(1).append("public void afterTextChanged(Editable s)\n")
				.indent(1).append("{\n")
				.indent(2).append("value.setValue(s.toString());\n")
				.indent(1).append("}");

		return body.toString();
	}
}

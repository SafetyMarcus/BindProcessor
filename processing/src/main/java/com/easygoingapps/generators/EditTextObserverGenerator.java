package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class EditTextObserverGenerator extends SourceGenerator
{
	private static final String NAME = "EditTextObserver";

	public EditTextObserverGenerator()
	{
		super(NAME);
	}

	@Override
	public String getImports()
	{
		return new ImportBuilder()
				.appendEditText()
				.append("\n")
				.appendObserver()
				.appendState()
				.build();
	}

	@Override
	public String getInterfaces()
	{
		return "Observer<String>";
	}

	@Override
	public String getVariables()
	{
		VariablesBuilder builder = new VariablesBuilder();

		SourceVariable editText = new SourceVariable("private", "EditText", "editText");
		variables.add(editText);
		builder.appendVariable(editText);

		SourceVariable state = new SourceVariable("private", "State<String>", "state");
		variables.add(state);
		builder.appendVariable(state);

		return builder.build();
	}

	@Override
	public String getBody()
	{
		StringBuilder body = new StringBuilder();

		body.append(INDENT).append("@Override\n")
				.append(INDENT).append("public void onChange(String value)\n")
				.append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append("state.removeObserver(this);\n")
				.append(INDENT).append(INDENT).append("int cursorPosition = editText.getSelectionStart();\n")
				.append(INDENT).append(INDENT).append("if(cursorPosition == editText.length())\n")
				.append(INDENT).append(INDENT).append(INDENT).append("cursorPosition = -1;\n\n")
				.append(INDENT).append(INDENT).append("editText.setText(value);\n\n")
				.append(INDENT).append(INDENT).append("if(cursorPosition == -1)\n")
				.append(INDENT).append(INDENT).append(INDENT).append("cursorPosition = editText.length();\n\n")
				.append(INDENT).append(INDENT).append("try\n")
				.append(INDENT).append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append(INDENT).append("editText.setSelection(cursorPosition);\n")
				.append(INDENT).append(INDENT).append("}\n")
				.append(INDENT).append(INDENT).append("catch(IndexOutOfBoundsException e)\n")
				.append(INDENT).append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append(INDENT).append("editText.setSelection(editText.length());\n")
				.append(INDENT).append(INDENT).append("}\n\n")
				.append(INDENT).append(INDENT).append("state.addObserver(this);\n")
				.append(INDENT).append("}");

		return body.toString();
	}
}

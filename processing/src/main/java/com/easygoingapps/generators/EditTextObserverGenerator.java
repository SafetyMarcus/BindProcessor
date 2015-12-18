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
		CodeBuilder body = new CodeBuilder();

		body.appendIndent(1).appendOverride()
				.appendIndent(1).append("public void onChange(String value)\n")
				.appendIndent(1).append("{\n")
				.appendIndent(2).append("state.removeObserver(this);\n")
				.appendIndent(2).append("int cursorPosition = editText.getSelectionStart();\n")
				.appendIndent(2).append("if(cursorPosition == editText.length())\n")
				.appendIndent(3).append("cursorPosition = -1;\n\n")
				.appendIndent(2).append("editText.setText(value);\n\n")
				.appendIndent(2).append("if(cursorPosition == -1)\n")
				.appendIndent(3).append("cursorPosition = editText.length();\n\n")
				.appendIndent(2).append("try\n")
				.appendIndent(2).append("{\n")
				.appendIndent(3).append("editText.setSelection(cursorPosition);\n")
				.appendIndent(2).append("}\n")
				.appendIndent(2).append("catch(IndexOutOfBoundsException e)\n")
				.appendIndent(2).append("{\n")
				.appendIndent(3).append("editText.setSelection(editText.length());\n")
				.appendIndent(2).append("}\n\n")
				.appendIndent(2).append("state.addObserver(this);\n")
				.appendIndent(1).append("}");

		return body.toString();
	}
}

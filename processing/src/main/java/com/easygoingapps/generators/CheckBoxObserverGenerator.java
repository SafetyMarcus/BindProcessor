package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class CheckBoxObserverGenerator extends SourceGenerator
{
	private static final String NAME = "CheckboxObserver";

	public CheckBoxObserverGenerator()
	{
		super(NAME);
	}

	@Override
	public String getInterfaces()
	{
		return "Observer<Boolean>";
	}

	@Override
	public String getImports()
	{
		ImportBuilder imports = new ImportBuilder();

		imports.appendCheckbox()
				.appendCompoundButton()
				.appendObserver()
				.appendState();

		return imports.build();
	}

	@Override
	public String getVariables()
	{
		VariablesBuilder variables = new VariablesBuilder();

		SourceVariable checkbox = new SourceVariable("private", "CheckBox", "checkbox");
		variables.appendVariable(checkbox, false);
		this.variables.add(checkbox);

		SourceVariable state = new SourceVariable("private", "State<Boolean>", "state");
		variables.appendVariable(state, false);
		this.variables.add(state);

		return variables.build();
	}

	@Override
	public String getBody()
	{
		return new StringBuilder().append(INDENT).append("@Override\n")
				.append(INDENT).append("public void onChange(Boolean value)\n")
				.append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append("state.removeObserver(this);\n")
				.append(INDENT).append(INDENT).append("checkbox.setChecked(value);\n")
				.append(INDENT).append(INDENT).append("state.addObserver(this);\n")
				.append(INDENT).append("}").toString();
	}
}

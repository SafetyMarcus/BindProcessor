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
		return new CodeBuilder().appendIndent(1).appendOverride()
				.appendIndent(1).append("public void onChange(Boolean value)\n")
				.appendIndent(1).append("{\n")
				.appendIndent(2).append("state.removeObserver(this);\n")
				.appendIndent(2).append("checkbox.setChecked(value);\n")
				.appendIndent(2).append("state.addObserver(this);\n")
				.appendIndent(1).append("}").toString();
	}
}

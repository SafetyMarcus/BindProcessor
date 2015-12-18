package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class CheckBoxBinderGenerator extends SourceGenerator
{
	private static final String NAME = "CheckboxBinder";

	public CheckBoxBinderGenerator()
	{
		super(NAME);
	}

	@Override
	public String getInterfaces()
	{
		return "CheckBox.OnCheckedChangeListener";
	}

	@Override
	public String getImports()
	{
		ImportBuilder imports = new ImportBuilder();

		imports.appendCheckbox()
				.appendCompoundButton()
				.appendState();

		return imports.build();
	}

	@Override
	public String getVariables()
	{
		VariablesBuilder variables = new VariablesBuilder();

		SourceVariable state = new SourceVariable("private", "State<Boolean>", "value");
		variables.appendVariable(state, false);
		this.variables.add(state);

		return variables.build();
	}

	@Override
	public String getBody()
	{
		return new CodeBuilder().appendIndent(1).appendOverride()
				.appendIndent(1).append("public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)\n")
				.appendIndent(1).append("{\n")
				.appendIndent(2).append("value.setValue(isChecked);\n")
				.appendIndent(1).append("}").toString();
	}
}

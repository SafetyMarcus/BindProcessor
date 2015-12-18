package com.easygoingapps.generators;

/**
 * @author Marcus Hooper
 */
public class ViewBindingGenerator extends SourceGenerator
{
	public static final String MAPPINGS = "${mappings}";
	public static final String PACKAGE = "${package}";

	public ViewBindingGenerator(String className)
	{
		super(className + "Binding", false);
	}

	@Override
	public String getPackage()
	{
		return "package " + PACKAGE + ";";
	}

	@Override
	public String getImports()
	{
		ImportBuilder imports = new ImportBuilder();
		imports.appendObserverClass("CheckboxBinder")
				.appendObserverClass("CheckboxObserver")
				.appendObserverClass("EditTextBinder")
				.appendObserverClass("EditTextObserver")
				.appendObserverClass("TextViewObserver")
				.append("import android.app.Activity;")
				.append("import android.app.Fragment;\n")
				.appendCheckbox()
				.appendEditText()
				.appendImageView()
				.appendTextView()
				.appendView()
				.appendObserver()
				.appendState()
				.append("\nimport java.lang.reflect.Field;")
				.append("import java.util.ArrayList;")
				.append("import java.util.HashMap;");

		return imports.build();
	}

	@Override
	public String getVariables()
	{
		VariablesBuilder variables = new VariablesBuilder();

		SourceVariable states = new SourceVariable("private static", "ArrayList<State>", "states");
		variables.appendVariable(states);
		this.variables.add(states);

		SourceVariable observers = new SourceVariable("private static", "ArrayList<Observer>", "observers");
		variables.appendVariable(observers);
		this.variables.add(observers);

		return variables.build();
	}

	@Override
	public String getBody()
	{
		CodeBuilder body = new CodeBuilder();
		body.appendIndent(1).append("public static void watch(Activity activity)\n")
				.appendIndent(1).append("{\n")
				.appendIndent(2).append("states = new ArrayList<>();\n")
				.appendIndent(2).append("observers = new ArrayList();\n")
				.append("\n");

		body.appendIndent(2).append("HashMap<Integer, String> stateMap = new HashMap<Integer, String>();\n")
				.appendIndent(2).append("String mappings = ").append(MAPPINGS).append(";\n")
				.appendIndent(2).append("mappings = mappings.substring(1, mappings.length() - 1);\n")
				.appendIndent(2).append("String[] values = mappings.split(\",\");\n");

		body.appendIndent(2).append("for(String value : values)\n")
				.appendIndent(2).append("{\n")
				.appendIndent(3).append("String[] map = value.trim().split(\"=\");\n")
				.appendIndent(3).append("stateMap.put(Integer.valueOf(map[0]), map[1]);\n")
				.appendIndent(2).append("}\n")
				.append("\n");

		body.appendIndent(2).append("for(Integer viewId : stateMap.keySet())\n")
				.appendIndent(2).append("{\n")
				.appendIndent(3).append("String name = stateMap.get(viewId);\n")
				.appendIndent(3).append("try\n")
				.appendIndent(3).append("{\n")
				.appendIndent(4).append("Field field = activity.getClass().getField(name);\n")
				.appendIndent(4).append("View view = activity.findViewById(viewId);\n")
				.append("\n")
				.appendIndent(4).append("Observer observer = null;\n")
				.appendIndent(4).append("if(view instanceof EditText)\n")
				.appendIndent(4).append("{\n")
				.appendIndent(5).append("State<String> value = (State<String>) field.get(activity);\n")
				.appendIndent(5).append("states.add(value);\n")
				.append("\n")
				.appendIndent(5).append("EditText editText = (EditText) view;\n")
				.appendIndent(5).append("editText.addTextChangedListener(new EditTextBinder(value));\n")
				.append("\n")
				.appendIndent(5).append("observer = new EditTextObserver(editText, value);\n")
				.appendIndent(5).append("value.addObserver(observer);\n")
				.appendIndent(5).append("value.setValue(value.getValue());\n")
				.appendIndent(4).append("}\n")
				.appendIndent(4).append("else if(view instanceof CheckBox)\n")
				.appendIndent(4).append("{\n")
				.appendIndent(5).append("State<Boolean> value = (State<Boolean>) field.get(activity);\n")
				.appendIndent(5).append("states.add(value);\n")
				.append("\n")
				.appendIndent(5).append("CheckBox checkbox = (CheckBox) view;\n")
				.appendIndent(5).append("checkbox.setOnCheckedChangeListener(new CheckboxBinder(value));\n")
				.append("\n")
				.appendIndent(5).append("observer = new CheckboxObserver(checkbox, value);\n")
				.appendIndent(5).append("value.addObserver(observer);\n")
				.appendIndent(5).append("value.setValue(value.getValue());\n")
				.appendIndent(4).append("}\n")
				.appendIndent(4).append("else if(view instanceof ImageView)\n")
				.appendIndent(4).append("{\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("State<Integer> value = (State<Integer>) field.get(activity);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("states.add(value);\n")
//				.append("\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("ImageView imageView = (ImageView) view;\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("observer = new ImageViewObserver(imageView);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.addObserver(observer);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.setValue(value.getValue());\n")
				.appendIndent(4).append("}\n")
				.appendIndent(4).append("else if(view instanceof TextView)\n")
				.appendIndent(4).append("{\n")
				.appendIndent(5).append("State<Object> value = (State<Object>) field.get(activity);\n")
				.appendIndent(5).append("states.add(value);\n")
				.append("\n")
				.appendIndent(5).append("TextView textView = (TextView) view;\n")
				.appendIndent(5).append("observer = new TextViewObserver(textView);\n")
				.appendIndent(5).append("value.addObserver(observer);\n")
				.appendIndent(5).append("value.setValue(value.getValue());\n")
				.appendIndent(4).append("}\n")
				.append("\n")
				.appendIndent(4).append("observers.add(observer);\n")
				.appendIndent(3).append("}\n")
				.appendIndent(3).append("catch(NoSuchFieldException | IllegalAccessException e)\n")
				.appendIndent(3).append("{\n")
				.appendIndent(4).append("e.printStackTrace();\n")
				.appendIndent(3).append("}\n")
				.appendIndent(2).append("}\n")
				.appendIndent(1).append("}");

		return body.toString();
	}
}

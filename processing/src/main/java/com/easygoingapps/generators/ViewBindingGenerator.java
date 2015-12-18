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
		StringBuilder body = new StringBuilder();
		body.append(INDENT).append("public static void watch(Activity activity)\n")
				.append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append("states = new ArrayList<>();\n")
				.append(INDENT).append(INDENT).append("observers = new ArrayList();\n")
				.append("\n");

		body.append(INDENT).append(INDENT).append("HashMap<Integer, String> stateMap = new HashMap<Integer, String>();\n")
				.append(INDENT).append(INDENT).append("String mappings = ").append(MAPPINGS).append(";\n")
				.append(INDENT).append(INDENT).append("mappings = mappings.substring(1, mappings.length() - 1);\n")
				.append(INDENT).append(INDENT).append("String[] values = mappings.split(\",\");\n");

		body.append(INDENT).append(INDENT).append("for(String value : values)\n")
				.append(INDENT).append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append(INDENT).append("String[] map = value.trim().split(\"=\");\n")
				.append(INDENT).append(INDENT).append(INDENT).append("stateMap.put(Integer.valueOf(map[0]), map[1]);\n")
				.append(INDENT).append(INDENT).append("}\n")
				.append("\n");

		body.append(INDENT).append(INDENT).append("for(Integer viewId : stateMap.keySet())\n")
				.append(INDENT).append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append(INDENT).append("String name = stateMap.get(viewId);\n")
				.append(INDENT).append(INDENT).append(INDENT).append("try\n")
				.append(INDENT).append(INDENT).append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("Field field = activity.getClass().getField(name);\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("View view = activity.findViewById(viewId);\n")
				.append("\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("Observer observer = null;\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("if(view instanceof EditText)\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("{\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("State<String> value = (State<String>) field.get(activity);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("states.add(value);\n")
//				.append("\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("EditText editText = (EditText) view;\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("editText.addTextChangedListener(new EditTextBinder(value));\n")
//				.append("\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("observer = new EditTextObserver(editText, value);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.addObserver(observer);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.setValue(value.getValue());\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("}\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("else if(view instanceof CheckBox)\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("State<Boolean> value = (State<Boolean>) field.get(activity);\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("states.add(value);\n")
				.append("\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("CheckBox checkbox = (CheckBox) view;\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("checkbox.setOnCheckedChangeListener(new CheckboxBinder(value));\n")
				.append("\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("observer = new CheckboxObserver(checkbox, value);\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.addObserver(observer);\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.setValue(value.getValue());\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("}\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("else if(view instanceof ImageView)\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("{\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("State<Integer> value = (State<Integer>) field.get(activity);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("states.add(value);\n")
//				.append("\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("ImageView imageView = (ImageView) view;\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("observer = new ImageViewObserver(imageView);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.addObserver(observer);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.setValue(value.getValue());\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("}\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("else if(view instanceof TextView)\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("{\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("State<Object> value = (State<Object>) field.get(activity);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("states.add(value);\n")
//				.append("\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("TextView textView = (TextView) view;\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("observer = new TextViewObserver(textView);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.addObserver(observer);\n")
//				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("value.setValue(value.getValue());\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("}\n")
				.append("\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("observers.add(observer);\n")
				.append(INDENT).append(INDENT).append(INDENT).append("}\n")
				.append(INDENT).append(INDENT).append(INDENT).append("catch(NoSuchFieldException | IllegalAccessException e)\n")
				.append(INDENT).append(INDENT).append(INDENT).append("{\n")
				.append(INDENT).append(INDENT).append(INDENT).append(INDENT).append("e.printStackTrace();\n")
				.append(INDENT).append(INDENT).append(INDENT).append("}\n")
				.append(INDENT).append(INDENT).append("}\n")
				.append(INDENT).append("}");

		return body.toString();
	}
}

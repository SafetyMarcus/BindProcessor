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
		imports.appendBinderClass("CheckboxBinder")
				.appendObserverClass("CheckboxObserver")
				.appendBinderClass("EditTextBinder")
				.appendObserverClass("EditTextObserver")
				.appendObserverClass("TextViewObserver")
				.appendObserverClass("ImageViewObserver")
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
		addActivityWatch(body);
		addFragmentWatch(body);
		addStartWatching(body);

		return body.toString();
	}

	private void addActivityWatch(CodeBuilder body)
	{
		body.indent(1).append("public static void watch(Activity activity)\n")
				.indent(1).append("{\n")
				.indent(2).append("states = new ArrayList<>();\n")
				.indent(2).append("observers = new ArrayList();\n")
				.append("\n");

		body.indent(2).append("HashMap<Integer, String> stateMap = new HashMap<Integer, String>();\n")
				.indent(2).append("String mappings = ").append(MAPPINGS).append(";\n")
				.indent(2).append("mappings = mappings.substring(1, mappings.length() - 1);\n")
				.indent(2).append("String[] values = mappings.split(\",\");\n");

		body.indent(2).append("for(String value : values)\n")
				.indent(2).append("{\n")
				.indent(3).append("String[] map = value.trim().split(\"=\");\n")
				.indent(3).append("stateMap.put(Integer.valueOf(map[0]), map[1]);\n")
				.indent(2).append("}\n")
				.append("\n");

		body.indent(2).append("for(Integer viewId : stateMap.keySet())\n")
				.indent(2).append("{\n")
				.indent(3).append("String name = stateMap.get(viewId);\n")
				.indent(3).append("try\n")
				.indent(3).append("{\n")
				.indent(4).append("Field field = activity.getClass().getField(name);\n")
				.indent(4).append("View view = activity.findViewById(viewId);\n")
				.append("\n")
				.indent(4).append("observers.add(startWatching(field, view, activity));\n")
				.indent(3).append("}\n")
				.indent(3).append("catch(NoSuchFieldException | IllegalAccessException e)\n")
				.indent(3).append("{\n")
				.indent(4).append("e.printStackTrace();\n")
				.indent(3).append("}\n")
				.indent(2).append("}\n")
				.indent(1).append("}\n\n");
	}

	private void addFragmentWatch(CodeBuilder body)
	{
		body.indent(1).append("public static void watch(View layout, Fragment fragment)\n")
				.indent(1).append("{\n")
				.indent(2).append("states = new ArrayList<>();\n")
				.indent(2).append("observers = new ArrayList();\n")
				.append("\n");

		body.indent(2).append("HashMap<Integer, String> stateMap = new HashMap<Integer, String>();\n")
				.indent(2).append("String mappings = ").append(MAPPINGS).append(";\n")
				.indent(2).append("mappings = mappings.substring(1, mappings.length() - 1);\n")
				.indent(2).append("String[] values = mappings.split(\",\");\n");

		body.indent(2).append("for(String value : values)\n")
				.indent(2).append("{\n")
				.indent(3).append("String[] map = value.trim().split(\"=\");\n")
				.indent(3).append("stateMap.put(Integer.valueOf(map[0]), map[1]);\n")
				.indent(2).append("}\n")
				.append("\n");

		body.indent(2).append("for(Integer viewId : stateMap.keySet())\n")
				.indent(2).append("{\n")
				.indent(3).append("String name = stateMap.get(viewId);\n")
				.indent(3).append("try\n")
				.indent(3).append("{\n")
				.indent(4).append("Field field = fragment.getClass().getField(name);\n")
				.indent(4).append("View view = layout.findViewById(viewId);\n")
				.append("\n")
				.indent(4).append("observers.add(startWatching(field, view, fragment));\n")
				.indent(3).append("}\n")
				.indent(3).append("catch(NoSuchFieldException | IllegalAccessException e)\n")
				.indent(3).append("{\n")
				.indent(4).append("e.printStackTrace();\n")
				.indent(3).append("}\n")
				.indent(2).append("}\n")
				.indent(1).append("}\n\n");
	}

	private void addStartWatching(CodeBuilder body)
	{
		body.indent(1).append("private static Observer startWatching(Field field, View view, Object parent) throws IllegalAccessException\n")
				.indent(1).append("{\n")
				.indent(2).append("Observer observer = null;\n")
				.indent(2).append("if(view instanceof EditText)\n")
				.indent(2).append("{\n")
				.indent(3).append("State<String> value = (State<String>) field.get(parent);\n")
				.indent(3).append("states.add(value);\n")
				.append("\n")
				.indent(3).append("EditText editText = (EditText) view;\n")
				.indent(3).append("editText.addTextChangedListener(new EditTextBinder(value));\n")
				.append("\n")
				.indent(3).append("observer = new EditTextObserver(editText, value);\n")
				.indent(3).append("value.addObserver(observer);\n")
				.indent(3).append("value.setValue(value.getValue());\n")
				.indent(2).append("}\n")
				.indent(2).append("else if(view instanceof CheckBox)\n")
				.indent(2).append("{\n")
				.indent(3).append("State<Boolean> value = (State<Boolean>) field.get(parent);\n")
				.indent(3).append("states.add(value);\n")
				.append("\n")
				.indent(3).append("CheckBox checkbox = (CheckBox) view;\n")
				.indent(3).append("checkbox.setOnCheckedChangeListener(new CheckboxBinder(value));\n")
				.append("\n")
				.indent(3).append("observer = new CheckboxObserver(checkbox, value);\n")
				.indent(3).append("value.addObserver(observer);\n")
				.indent(3).append("value.setValue(value.getValue());\n")
				.indent(2).append("}\n")
				.indent(2).append("else if(view instanceof ImageView)\n")
				.indent(2).append("{\n")
				.indent(3).append("State<Integer> value = (State<Integer>) field.get(parent);\n")
				.indent(3).append("states.add(value);\n")
				.append("\n")
				.indent(3).append("ImageView imageView = (ImageView) view;\n")
				.indent(3).append("observer = new ImageViewObserver(imageView);\n")
				.indent(3).append("value.addObserver(observer);\n")
				.indent(3).append("value.setValue(value.getValue());\n")
				.indent(2).append("}\n")
				.indent(2).append("else if(view instanceof TextView)\n")
				.indent(2).append("{\n")
				.indent(3).append("State<Object> value = (State<Object>) field.get(parent);\n")
				.indent(3).append("states.add(value);\n")
				.append("\n")
				.indent(3).append("TextView textView = (TextView) view;\n")
				.indent(3).append("observer = new TextViewObserver(textView);\n")
				.indent(3).append("value.addObserver(observer);\n")
				.indent(3).append("value.setValue(value.getValue());\n")
				.indent(2).append("}\n")
				.indent(2).append("return observer;\n")
				.indent(1).append("}");
	}
}

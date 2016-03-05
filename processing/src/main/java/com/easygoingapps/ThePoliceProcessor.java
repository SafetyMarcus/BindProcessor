package com.easygoingapps;

import com.easygoingapps.annotations.Observe;
import com.easygoingapps.generators.CheckBoxBinderGenerator;
import com.easygoingapps.generators.CheckBoxObserverGenerator;
import com.easygoingapps.generators.EditTextBinderGenerator;
import com.easygoingapps.generators.EditTextObserverGenerator;
import com.easygoingapps.generators.ImageViewObserverGenerator;
import com.easygoingapps.generators.SourceGenerator;
import com.easygoingapps.generators.TextViewObserverGenerator;
import com.easygoingapps.generators.ThePoliceGenerator;
import com.easygoingapps.generators.ViewBindingGenerator;
import com.easygoingapps.utils.BindState;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Set;

import static com.easygoingapps.generators.ViewBindingGenerator.MAPPINGS;
import static com.easygoingapps.generators.ViewBindingGenerator.PACKAGE;

@SupportedAnnotationTypes("com.easygoingapps.annotations.Observe")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class ThePoliceProcessor extends AbstractProcessor
{
	Filer filer;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv)
	{
		super.init(processingEnv);
		filer = processingEnv.getFiler();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		try
		{
			setUpObservers();
		}
		catch(IOException e)
		{
			processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "IOException setting up observers: " + e.getMessage());
		}

		try
		{
			ArrayList<BindState> states = setUpStates(roundEnv);
			createBindingClasses(states);
		}
		catch(IOException e)
		{
			processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "IOException creating bindings: " + e.getMessage());
			return false;
		}

		return true;
	}

	private void setUpObservers() throws IOException
	{
		ArrayList<SourceGenerator> generators = new ArrayList<>();
		generators.add(new CheckBoxBinderGenerator());
		generators.add(new CheckBoxObserverGenerator());
		generators.add(new EditTextBinderGenerator());
		generators.add(new EditTextObserverGenerator());
		generators.add(new ImageViewObserverGenerator());
		generators.add(new TextViewObserverGenerator());
		for(SourceGenerator generator : generators)
		{
			JavaFileObject jfo = filer.createSourceFile(generator.className);
			Writer writer = jfo.openWriter();
			writer.write(generator.generate());
			writer.close();
		}
	}

	private ArrayList<BindState> setUpStates(RoundEnvironment roundEnv)
	{
		ArrayList<String> classes = new ArrayList<>();
		ArrayList<BindState> states = new ArrayList<>();
		for(Element element : roundEnv.getElementsAnnotatedWith(Observe.class))
		{
			if(element.getKind() == ElementKind.FIELD)
			{
				VariableElement variableElement = (VariableElement) element;
				TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
				PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

				BindState state = null;
				String className = classElement.getSimpleName().toString();
				if(!classes.contains(className))
				{
					classes.add(className);
					state = new BindState();
					state.className = className;
					state.packageName = packageElement.getQualifiedName().toString();
					state.qualifiedPackageName = packageElement.getQualifiedName().toString();
					state.qualifiedClassName = classElement.getQualifiedName().toString();
					states.add(state);
				}
				else
				{
					for(BindState bindState : states)
					{
						if(bindState.className.equals(className))
						{
							state = bindState;
							break;
						}
					}
				}

				if(state != null)
					state.mappings.put(element.getAnnotation(Observe.class).value(), variableElement.getSimpleName().toString());
			}
		}

		return states;
	}

	private void createBindingClasses(ArrayList<BindState> states) throws IOException
	{
		for(BindState state : states)
		{
			JavaFileObject jfo = filer.createSourceFile(state.qualifiedClassName + "Binding");
			Writer writer = jfo.openWriter();

			ViewBindingGenerator generator = new ViewBindingGenerator(state.className);
			String viewBindings = generator.generate();

			viewBindings = viewBindings.replace(PACKAGE, state.packageName);
			viewBindings = viewBindings.replace(MAPPINGS, "\"" + state.mappings.toString() + "\"");

			writer.write(viewBindings);
			writer.close();
		}

		ThePoliceGenerator policeGenerator = new ThePoliceGenerator(states);
		JavaFileObject jfo = filer.createSourceFile(states.get(0).packageName + "." + policeGenerator.className);
		Writer writer = jfo.openWriter();
		writer.write(policeGenerator.generate());
		writer.close();
	}
}

package com.easygoingapps;

import com.easygoingapps.annotations.Observe;
import com.easygoingapps.generators.CheckBoxBinderGenerator;
import com.easygoingapps.generators.SourceGenerator;
import com.easygoingapps.utils.BindState;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.texen.util.PropertiesUtil;

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
import java.util.Properties;
import java.util.Set;

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

		ArrayList<BindState> states = setUpStates(roundEnv);
		try
		{
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
			Properties props = new PropertiesUtil().load("velocity.properties");
			VelocityEngine ve = new VelocityEngine(props);
			ve.init();
			VelocityContext vc = new VelocityContext();
			vc.put("packageName", state.qualifiedPackageName);
			vc.put("className", state.className);
			vc.put("mappings", "\"" + state.mappings + "\"");

			Template template = ve.getTemplate("viewbinding.vm");

			JavaFileObject jfo = filer.createSourceFile(state.qualifiedClassName + "Binding");
			Writer writer = jfo.openWriter();
			template.merge(vc, writer);
			writer.close();
		}
	}
}

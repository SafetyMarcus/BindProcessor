package com.easygoingapps;

import com.easygoingapps.annotations.Bind;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.texen.util.PropertiesUtil;
import utils.BindState;

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

@SupportedAnnotationTypes("com.easygoingapps.annotations.Bind")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BindProcessor extends AbstractProcessor
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
		ArrayList<String> classes = new ArrayList<>();
		ArrayList<BindState> states = new ArrayList<>();
		for(Element element : roundEnv.getElementsAnnotatedWith(Bind.class))
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

				if(state == null)
				{
					processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Binding state was null");
					return false;
				}

				state.mappings.put(element.getAnnotation(Bind.class).value(), variableElement.getSimpleName().toString());
			}
		}

		for(BindState state : states)
		{

			try
			{
				Properties props = new PropertiesUtil().load("velocity.properties");
				VelocityEngine ve = new VelocityEngine(props);
				ve.init();
				VelocityContext vc = new VelocityContext();
				vc.put("packageName", state.qualifiedPackageName);
				vc.put("className", state.className);
				vc.put("mappings", "\"" + state.mappings + "\"");

				Template template = ve.getTemplate("viewbinding.vm");

				JavaFileObject jfo = filer.createSourceFile(state.qualifiedClassName + "ViewBinding");
				Writer writer = jfo.openWriter();
				template.merge(vc, writer);
				writer.close();
			}
			catch(IOException e)
			{
				processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "IOException: " + e.getMessage());
			}
		}
		return true;
	}
}

package com.easygoingapps;

import com.easygoingapps.annotations.Bind;

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
import java.io.BufferedWriter;
import java.io.IOException;
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
		for(Element element : roundEnv.getElementsAnnotatedWith(Bind.class))
		{
			if(element.getKind() == ElementKind.FIELD)
			{
				VariableElement variableElement = (VariableElement) element;
				TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
				PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
				try
				{
					String className = classElement.getSimpleName() + "ViewBinding";
					JavaFileObject jfo = filer.createSourceFile(classElement.getQualifiedName() + "ViewBinding");
					BufferedWriter writer = new BufferedWriter(jfo.openWriter());
					writer.append("package ");
					writer.append(packageElement.getQualifiedName());
					writer.append(";");
					writer.newLine();
					writer.newLine();
					writer.append("import android.app.Activity;");
					writer.newLine();
					writer.append("import android.widget.EditText;");
					writer.newLine();
					writer.newLine();
					writer.append("public class ").append(className);
					writer.newLine();
					writer.append("{");
					writer.newLine();
					//Start of code

					//Start of constructor
					writer.append("    public ").append(className).append(" (Activity activity)");
					writer.newLine();
					writer.append("    {");
					writer.newLine();
					writer.append("        EditText editText = (EditText) activity.findViewById(")
							.append("" + element.getAnnotation(Bind.class).value())
							.append(");");
					writer.newLine();
					writer.append("    }");
					writer.newLine();
					//End of constructor

					writer.append("}");
					writer.close();
				}
				catch(IOException e)
				{
					processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "code generation failed for " + variableElement.getSimpleName(), element);
				}
			}
		}
		return true;
	}
}

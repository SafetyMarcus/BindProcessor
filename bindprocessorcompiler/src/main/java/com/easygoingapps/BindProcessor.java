package com.easygoingapps;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("com.easygoingapps.annotations.Bind")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class BindProcessor extends AbstractProcessor
{
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
//		for(Element element : roundEnv.getElementsAnnotatedWith(Bind.class))
//			processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Actually processing something", element);
		return false;
	}
}

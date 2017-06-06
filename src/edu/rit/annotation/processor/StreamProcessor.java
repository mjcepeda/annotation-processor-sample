package edu.rit.annotation.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import edu.rit.annotation.StreamsFactory;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes("edu.rit.annotation.StreamsFactory")
public class StreamProcessor extends AbstractProcessor {

	//private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		//typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		// iterate over all @StreamsFactory annotated elements
		for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(StreamsFactory.class)) {
			// Check if a class has been annotated with @StreamsFactory
			if (annotatedElement.getKind() != ElementKind.CLASS) {
				error(annotatedElement, "Only classes can be annotated with @%s", StreamsFactory.class.getSimpleName());
				return true; // Exit processing
			}

			// TODO MJCG Include validations (no interface, no abstract class,
			// default constructor...)
			// We can cast it, because we know that it of ElementKind.CLASS
			TypeElement typeElement = (TypeElement) annotatedElement;
			
			try {
				generateCode(typeElement, elementUtils, filer);
			} catch (IOException e) {
				error(null, e.getMessage());
			}

		}
		return true;
	}

	private void error(Element e, String msg, Object... args) {
		messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
	}

	public void generateCode(TypeElement typeElement, Elements elementUtils, Filer filer) throws IOException {
		//TODO MJCG - This method will have the logic for creating the stream pipeline based on the query
		StreamsFactory annotation = typeElement.getAnnotation(StreamsFactory.class);
	    String query = annotation.query();
	    String queryName = annotation.name();

		JavaFileObject jfo = filer.createSourceFile("edu.rit.stream.StreamQueries");
		Writer writer = jfo.openWriter();

		writer.write("package edu.rit.stream;\n");
		writer.write("public final class StreamQueries {\n\n");
		writer.write("\t public void get" + queryName + "(){ System.out.println(\"Hello world!\");}\n");
		writer.write("}\n");
		writer.close();
	}
}

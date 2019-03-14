package com.naver.android.compiler;

import com.google.auto.service.AutoService;
import com.naver.android.annotation.RequireControlTower;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import static javax.tools.Diagnostic.Kind.ERROR;

@SuppressWarnings("ALL")
@AutoService(Processor.class)
public class SvcProcessor extends AbstractProcessor {

  private Messager messager;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Collections.singleton(RequireControlTower.class.getCanonicalName());
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latest();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
    if (annotations.isEmpty()) {
      return true;
    }

    roundEnvironment
        .getElementsAnnotatedWith(RequireControlTower.class)
        .stream()
        .map(annotatedType -> (TypeElement) annotatedType)
        .forEach(
            annotatedType -> {
              try {
                checkTypeValidation(annotatedType);
                processControlTower(annotatedType);
              } catch (IllegalAccessException e) {
                showProcessErrorLog(e.getMessage(), annotatedType);
              }
            });

    return true;
  }

  private void processControlTower(TypeElement annotatedType) {

  }

  private void checkTypeValidation(TypeElement annotatedType) throws IllegalAccessException {
    if (!annotatedType.getKind().isClass()) {
      throw new IllegalAccessException("Only classes can be annotated with @PreferenceRoom");
    } else if (annotatedType.getModifiers().contains(Modifier.FINAL)) {
      showProcessErrorLog("class modifier should not be final", annotatedType);
    } else if (annotatedType.getModifiers().contains(Modifier.PRIVATE)) {
      showProcessErrorLog("class modifier should not be private", annotatedType);
    }
  }

  private void showProcessErrorLog(String message, Element element) {
    messager.printMessage(ERROR, message, element);
  }
}

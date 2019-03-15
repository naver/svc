package com.naver.android.compiler;

import com.google.auto.service.AutoService;
import com.google.common.base.VerifyException;
import com.naver.android.annotation.RequireControlTower;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static javax.tools.Diagnostic.Kind.ERROR;

@SuppressWarnings("ALL")
@AutoService(Processor.class)
public class SvcProcessor extends AbstractProcessor {

  private Messager messager;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    this.messager = processingEnv.getMessager();
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
    try {
      ControlTowerAnnotatedClass annotatedClazz =
          new ControlTowerAnnotatedClass(annotatedType, processingEnv.getElementUtils());
      PackageElement packageElement =
          processingEnv.getElementUtils().getPackageOf(annotatedClazz.annotatedElement);
      String packageName =
          packageElement.isUnnamed() ? null : packageElement.getQualifiedName().toString();
      generateProccessControlTower(packageName, annotatedClazz);
    } catch (VerifyException e) {
      showProcessErrorLog(e.getMessage(), annotatedType);
    }
  }

  private void generateProccessControlTower(
      String packageName,
      ControlTowerAnnotatedClass annotatedClazz) {
    try {
      ControlTowerGenerator controlTowerGenerator =
          new ControlTowerGenerator(packageName, annotatedClazz);
      TypeSpec controlTowerClazz = controlTowerGenerator.generate();
      JavaFile.builder(packageName, controlTowerClazz).build().writeTo(processingEnv.getFiler());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void checkTypeValidation(TypeElement annotatedType) throws IllegalAccessException {
    if (!annotatedType.getKind().isClass()) {
      throw new IllegalAccessException("Only classes can be annotated with @RequireControlTower.");
    }
  }

  private void showProcessErrorLog(String message, Element element) {
    messager.printMessage(ERROR, message, element);
  }
}

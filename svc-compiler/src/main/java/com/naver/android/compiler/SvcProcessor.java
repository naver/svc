package com.naver.android.compiler;

import com.google.auto.service.AutoService;
import com.google.common.base.VerifyException;
import com.naver.android.annotation.ControlTower;
import com.naver.android.annotation.RequireControlTower;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

@SuppressWarnings("unused")
@AutoService(Processor.class)
public class SvcProcessor extends AbstractProcessor {

  private Map<String, RequireControlTowerAnnotatedClass> annotatedRequireControlTowerMap;
  private Messager messager;

  @Override
  public synchronized void init(ProcessingEnvironment processingEnvironment) {
    super.init(processingEnvironment);
    annotatedRequireControlTowerMap = new HashMap<>();
    this.messager = processingEnv.getMessager();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> supportedTypes = new HashSet<>();
    supportedTypes.add(RequireControlTower.class.getCanonicalName());
    supportedTypes.add(ControlTower.class.getCanonicalName());
    return supportedTypes;
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
                checkRequireControlTowerValidation(annotatedType);
                processRequireControlTower(annotatedType);
              } catch (IllegalAccessException e) {
                showProcessErrorLog(e.getMessage(), annotatedType);
              }
            });

    roundEnvironment
        .getElementsAnnotatedWith(ControlTower.class)
        .stream()
        .map(annotatedType -> (TypeElement) annotatedType)
        .forEach(
            annotatedType -> {
              try {
                checkControlTowerValidation(annotatedType);
                processControlTowers(annotatedType);
              } catch (IllegalAccessException e) {
                showProcessErrorLog(e.getMessage(), annotatedType);
              }
            }
        );

    return true;
  }

  private void processRequireControlTower(TypeElement annotatedType) {
    try {
      RequireControlTowerAnnotatedClass annotatedClazz =
          new RequireControlTowerAnnotatedClass(annotatedType, processingEnv.getElementUtils());
      this.annotatedRequireControlTowerMap.put(annotatedClazz.controlTowerName, annotatedClazz);
      PackageElement packageElement =
          processingEnv.getElementUtils().getPackageOf(annotatedClazz.annotatedElement);
      String packageName =
          packageElement.isUnnamed() ? null : packageElement.getQualifiedName().toString();
      generateProcessControlTower(packageName, annotatedClazz);
    } catch (VerifyException e) {
      showProcessErrorLog(e.getMessage(), annotatedType);
    }
  }

  private void processControlTowers(TypeElement annotatedType) {
    try {
      RequireControlTowerAnnotatedClass requireControlTowerAnnotatedClass
          = annotatedRequireControlTowerMap.get(annotatedType.getSimpleName().toString());
      if (requireControlTowerAnnotatedClass != null) {
        ControlTowerAnnotatedClass annotatedClazz =
            new ControlTowerAnnotatedClass(annotatedType, processingEnv.getElementUtils());
        PackageElement packageElement =
            processingEnv.getElementUtils().getPackageOf(annotatedClazz.annotatedElement);
        String packageName =
            packageElement.isUnnamed() ? null : packageElement.getQualifiedName().toString();
        generateProcessScreenExtends(packageName, annotatedClazz, requireControlTowerAnnotatedClass);
      }
    } catch (VerifyException e) {
      showProcessErrorLog(e.getMessage(), annotatedType);
    }
  }

  private void generateProcessControlTower(
      String packageName,
      RequireControlTowerAnnotatedClass annotatedClazz) {
    try {
      ControlTowerGenerator controlTowerGenerator =
          new ControlTowerGenerator(packageName, annotatedClazz);
      TypeSpec controlTowerClazz = controlTowerGenerator.generate();
      JavaFile.builder(packageName, controlTowerClazz).build().writeTo(processingEnv.getFiler());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void generateProcessScreenExtends(
      String packageName,
      ControlTowerAnnotatedClass controlTowerAnnotatedClass,
      RequireControlTowerAnnotatedClass requireControlTowerAnnotatedClass) {
    try {
      ScreenExtendsGenerator screenExtendsGenerator=
          new ScreenExtendsGenerator(packageName, controlTowerAnnotatedClass, requireControlTowerAnnotatedClass);
      TypeSpec controlTowerClazz = screenExtendsGenerator.generate();
      JavaFile.builder(packageName, controlTowerClazz).build().writeTo(processingEnv.getFiler());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void checkRequireControlTowerValidation(TypeElement annotatedType) throws IllegalAccessException {
    if (!annotatedType.getKind().isClass()) {
      throw new IllegalAccessException("Only classes can be annotated with @RequireControlTower.");
    }
  }

  private void checkControlTowerValidation(TypeElement annotatedType) throws IllegalAccessException {
    if (!annotatedType.getKind().isClass()) {
      throw new IllegalAccessException("Only classes can be annotated with @ControlTower.");
    }
  }

  private void showProcessErrorLog(String message, Element element) {
    messager.printMessage(ERROR, message, element);
  }
}

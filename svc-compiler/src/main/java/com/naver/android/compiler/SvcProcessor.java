/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.naver.android.compiler;

import static javax.tools.Diagnostic.Kind.ERROR;

import com.google.auto.service.AutoService;
import com.google.common.base.VerifyException;
import com.naver.android.annotation.ControlTower;
import com.naver.android.annotation.SvcActivity;
import com.naver.android.annotation.SvcDialogFragment;
import com.naver.android.annotation.SvcFragment;
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

@SuppressWarnings("unused")
@AutoService(Processor.class)
public class SvcProcessor extends AbstractProcessor {

    private Map<String, ControlTowerAnnotatedClass> annotatedControlTowerMap;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        annotatedControlTowerMap = new HashMap<>();
        this.messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedTypes = new HashSet<>();
        supportedTypes.add(ControlTower.class.getCanonicalName());
        supportedTypes.add(SvcFragment.class.getCanonicalName());
        supportedTypes.add(SvcDialogFragment.class.getCanonicalName());
        supportedTypes.add(SvcActivity.class.getCanonicalName());
        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(
            Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        if (annotations.isEmpty()) {
            return true;
        }

        roundEnvironment
                .getElementsAnnotatedWith(ControlTower.class)
                .stream()
                .map(annotatedType -> (TypeElement) annotatedType)
                .forEach(
                        annotatedType -> {
                            try {
                                checkIsClassType(
                                        annotatedType,
                                        "Only classes can be annotated with @ControlTower.");
                                processControlTower(annotatedType);
                            } catch (IllegalAccessException e) {
                                showProcessErrorLog(e.getMessage(), annotatedType);
                            }
                        });

        roundEnvironment
                .getElementsAnnotatedWith(SvcActivity.class)
                .stream()
                .map(annotatedType -> (TypeElement) annotatedType)
                .forEach(
                        annotatedType -> {
                            try {
                                checkIsClassType(
                                        annotatedType,
                                        "Only classes can be annotated with @SvcActivity.");
                                processScreen(annotatedType);
                            } catch (IllegalAccessException e) {
                                showProcessErrorLog(e.getMessage(), annotatedType);
                            }
                        });

        roundEnvironment
                .getElementsAnnotatedWith(SvcFragment.class)
                .stream()
                .map(annotatedType -> (TypeElement) annotatedType)
                .forEach(
                        annotatedType -> {
                            try {
                                checkIsClassType(
                                        annotatedType,
                                        "Only classes can be annotated with @SvcFragment.");
                                processScreen(annotatedType);
                            } catch (IllegalAccessException e) {
                                showProcessErrorLog(e.getMessage(), annotatedType);
                            }
                        });

        roundEnvironment
                .getElementsAnnotatedWith(SvcDialogFragment.class)
                .stream()
                .map(annotatedType -> (TypeElement) annotatedType)
                .forEach(
                        annotatedType -> {
                            try {
                                checkIsClassType(
                                        annotatedType,
                                        "Only classes can be annotated with @SvcDialogFragment.");
                                processScreen(annotatedType);
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
            this.annotatedControlTowerMap.put(annotatedClazz.clazzName, annotatedClazz);
            PackageElement packageElement =
                    processingEnv.getElementUtils().getPackageOf(annotatedClazz.annotatedElement);
            String packageName =
                    packageElement.isUnnamed()
                            ? null
                            : packageElement.getQualifiedName().toString();
            generateProcessControlTower(packageName, annotatedClazz);
        } catch (VerifyException e) {
            showProcessErrorLog(e.getMessage(), annotatedType);
        }
    }

    private void generateProcessControlTower(
            String packageName, ControlTowerAnnotatedClass annotatedClazz) {
        try {
            ControlTowerGenerator controlTowerGenerator =
                    new ControlTowerGenerator(packageName, annotatedClazz);
            TypeSpec controlTowerClazz = controlTowerGenerator.generate();
            JavaFile.builder(packageName, controlTowerClazz)
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processScreen(TypeElement annotatedType) {
        try {
            ScreenAnnotatedClass annotatedClazz =
                    new ScreenAnnotatedClass(annotatedType, processingEnv.getElementUtils());
            PackageElement packageElement =
                    processingEnv.getElementUtils().getPackageOf(annotatedClazz.annotatedElement);
            String packageName =
                    packageElement.isUnnamed()
                            ? null
                            : packageElement.getQualifiedName().toString();
            generateScreen(packageName, annotatedClazz);
        } catch (VerifyException e) {
            showProcessErrorLog(e.getMessage(), annotatedType);
        }
    }

    private void generateScreen(String packageName, ScreenAnnotatedClass annotatedClazz) {
        try {
            ScreenExtendsGenerator controlTowerGenerator =
                    new ScreenExtendsGenerator(packageName, annotatedClazz);
            TypeSpec controlTowerClazz = controlTowerGenerator.generate();
            JavaFile.builder(packageName, controlTowerClazz)
                    .build()
                    .writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkIsClassType(TypeElement annotatedType, String errorMsg)
            throws IllegalAccessException {
        if (!annotatedType.getKind().isClass()) {
            throw new IllegalAccessException(errorMsg);
        }
    }

    private void showProcessErrorLog(String message, Element element) {
        messager.printMessage(ERROR, message, element);
    }
}

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

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import javax.lang.model.element.Modifier;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal", "unused"})
public class ScreenExtendsGenerator {

  private final String packageName;
  private final ScreenAnnotatedClass screenAnnotatedClass;

  public ScreenExtendsGenerator(String packageName, ScreenAnnotatedClass screenAnnotatedClass) {
    this.packageName = packageName;
    this.screenAnnotatedClass = screenAnnotatedClass;
  }

  public TypeSpec generate() {
    TypeSpec.Builder builder =
        TypeSpec.classBuilder(getExtendsName())
            .addJavadoc(
                "Generated by SVC processor. (https://github.com/naver/svc). Don't change this class.")
            .addModifiers(Modifier.PUBLIC)
            .addModifiers(Modifier.ABSTRACT)
            .addMethod(getCreateControlTowerMethodSpec())
            .addMethod(getCreateViewsMethodSpec())
            .superclass(this.screenAnnotatedClass.superClass);
    return builder.build();
  }

  private MethodSpec getCreateControlTowerMethodSpec() {
    return MethodSpec.methodBuilder("createControlTower")
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PUBLIC)
        .addStatement(String.format("return new %s()", screenAnnotatedClass.controlTowerName))
        .returns(screenAnnotatedClass.controlTower)
        .build();
  }

  private MethodSpec getCreateViewsMethodSpec() {
    return MethodSpec.methodBuilder("createViews")
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PUBLIC)
        .addStatement(String.format("return new %s()", screenAnnotatedClass.baseViewName))
        .returns(screenAnnotatedClass.baseView)
        .build();
  }

  private String getExtendsName() {
    return "SVC_" + this.screenAnnotatedClass.clazzName;
  }
}

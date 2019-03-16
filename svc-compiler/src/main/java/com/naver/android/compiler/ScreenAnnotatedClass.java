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

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import com.google.common.base.VerifyException;
import com.naver.android.annotation.SvcActivity;
import com.naver.android.annotation.SvcDialogFragment;
import com.naver.android.annotation.SvcFragment;
import com.naver.android.annotation.RequireControlTower;
import com.naver.android.annotation.RequireViews;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

@SuppressWarnings("WeakerAccess")
public class ScreenAnnotatedClass {

  public final TypeElement annotatedElement;
  public final String packageName;
  public final String clazzName;
  public ClassName baseView;
  public String baseViewName;
  public ClassName controlTower;
  public String controlTowerName;
  public String viewsMetaData;
  public TypeName superClass;

  public ScreenAnnotatedClass(TypeElement annotatedElement, Elements elementUtils)
      throws VerifyException {
    PackageElement packageElement = elementUtils.getPackageOf(annotatedElement);
    this.packageName =
        packageElement.isUnnamed() ? null : packageElement.getQualifiedName().toString();
    this.annotatedElement = annotatedElement;
    this.clazzName = annotatedElement.getSimpleName().toString();

    RequireViews requireViews = annotatedElement.getAnnotation(RequireViews.class);
    int packageIndexViews = requireViews.toString().lastIndexOf("=");
    String viewsPackage =
        requireViews
            .toString()
            .substring(packageIndexViews + 1, requireViews.toString().length() - 1);

    int indexView = viewsPackage.lastIndexOf(".");
    if (indexView == -1) indexView = viewsPackage.lastIndexOf("\\.");

    this.baseViewName = viewsPackage.substring(indexView + 1);
    this.baseView = ClassName.get(viewsPackage.substring(0, indexView), baseViewName);

    RequireControlTower requireControlTower =
        annotatedElement.getAnnotation(RequireControlTower.class);
    int packageIndexS0 = requireControlTower.toString().lastIndexOf("=");
    String controlTowerPackage =
        requireControlTower
            .toString()
            .substring(packageIndexS0 + 1, requireControlTower.toString().length() - 1);

    int indexCT = controlTowerPackage.lastIndexOf(".");
    if (indexCT == -1) indexCT = controlTowerPackage.lastIndexOf("\\.");

    this.controlTowerName = controlTowerPackage.substring(indexCT + 1);
    this.controlTower = ClassName.get(controlTowerPackage.substring(0, indexCT), controlTowerName);

    SvcActivity svcActivity = annotatedElement.getAnnotation(SvcActivity.class);
    SvcFragment svcFragment = annotatedElement.getAnnotation(SvcFragment.class);
    SvcDialogFragment svcDialogFragment = annotatedElement.getAnnotation(SvcDialogFragment.class);

    if (svcActivity != null) {
      ClassName svcActivityClassName =
          ClassName.get("com.naver.android.svc.core.screen", "SvcActivity");
      this.superClass = ParameterizedTypeName.get(svcActivityClassName, baseView);
    } else if (svcFragment != null) {
      ClassName svcFragmentClassName =
          ClassName.get("com.naver.android.svc.core.screen", "SvcFragment");
      this.superClass = ParameterizedTypeName.get(svcFragmentClassName, baseView);
    } else if (svcDialogFragment != null) {
      ClassName svcDialogFragmentClassName =
          ClassName.get("com.naver.android.svc.core.screen", "SvcDialogFragment");
      this.superClass = ParameterizedTypeName.get(svcDialogFragmentClassName, baseView);
    }
  }
}

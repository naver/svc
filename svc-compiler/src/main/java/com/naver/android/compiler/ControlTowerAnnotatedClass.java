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

import com.google.common.base.VerifyException;
import com.naver.android.annotation.RequireScreen;
import com.naver.android.annotation.RequireViews;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@SuppressWarnings("WeakerAccess")
public class ControlTowerAnnotatedClass {

    public final TypeElement annotatedElement;
    public final String packageName;
    public final String clazzName;
    public ClassName baseView;
    public String baseViewName;
    public ClassName screen;
    public String screenName;
    public String viewsMetaData;
    public TypeName superClass;

    public ControlTowerAnnotatedClass(TypeElement annotatedElement, Elements elementUtils)
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

        RequireScreen requireScreen = annotatedElement.getAnnotation(RequireScreen.class);
        int packageIndexS0 = requireScreen.toString().lastIndexOf("=");
        String screenPackage =
                requireScreen
                        .toString()
                        .substring(packageIndexS0 + 1, requireScreen.toString().length() - 1);

        int indexScreen = screenPackage.lastIndexOf(".");
        if (indexScreen == -1) indexScreen = screenPackage.lastIndexOf("\\.");

        this.screenName = screenPackage.substring(indexScreen + 1);
        this.screen = ClassName.get(screenPackage.substring(0, indexScreen), screenName);

        this.superClass = ClassName.get("com.naver.android.svc.core.controltower", "ControlTower");
    }
}

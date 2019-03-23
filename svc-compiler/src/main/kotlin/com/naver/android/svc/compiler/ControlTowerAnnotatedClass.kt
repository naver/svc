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
 */package com.naver.android.svc.compiler

import com.google.common.base.VerifyException
import com.naver.android.annotation.svc.RequireScreen
import com.naver.android.annotation.svc.RequireViews
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.TypeName
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

class ControlTowerAnnotatedClass @Throws(VerifyException::class)
constructor(val annotatedElement: TypeElement, elementUtils: Elements) {
    val packageName: String?
    val clazzName: String
    var baseView: ClassName
    var baseViewName: String
    var screen: ClassName
    var screenName: String
    var superClass: TypeName

    init {
        val packageElement = elementUtils.getPackageOf(annotatedElement)
        this.packageName = if (packageElement.isUnnamed) null else packageElement.qualifiedName.toString()
        this.clazzName = annotatedElement.simpleName.toString()

        val requireViews = annotatedElement.getAnnotation(RequireViews::class.java)
        val packageIndexViews = requireViews.toString().lastIndexOf("=")
        val viewsPackage = requireViews
            .toString()
            .substring(packageIndexViews + 1, requireViews.toString().length - 1)

        var indexView = viewsPackage.lastIndexOf(".")
        if (indexView == -1) indexView = viewsPackage.lastIndexOf("\\.")

        this.baseViewName = viewsPackage.substring(indexView + 1)
        this.baseView = ClassName.get(viewsPackage.substring(0, indexView), baseViewName)

        val requireScreen = annotatedElement.getAnnotation(RequireScreen::class.java)
        val packageIndexS0 = requireScreen.toString().lastIndexOf("=")
        val screenPackage = requireScreen
            .toString()
            .substring(packageIndexS0 + 1, requireScreen.toString().length - 1)

        var indexScreen = screenPackage.lastIndexOf(".")
        if (indexScreen == -1) indexScreen = screenPackage.lastIndexOf("\\.")

        this.screenName = screenPackage.substring(indexScreen + 1)
        this.screen = ClassName.get(screenPackage.substring(0, indexScreen), screenName)

        this.superClass = ClassName.get("com.naver.android.svc.core.controltower", "ControlTower")
    }
}

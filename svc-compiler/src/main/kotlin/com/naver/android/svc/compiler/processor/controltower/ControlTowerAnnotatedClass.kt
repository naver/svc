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
 */package com.naver.android.svc.compiler.processor.controltower

import com.google.common.base.VerifyException
import com.naver.android.svc.annotation.RequireScreen
import com.naver.android.svc.annotation.RequireViews
import com.naver.android.svc.compiler.processor.CommonAnnotatedClass
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.TypeName
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

class ControlTowerAnnotatedClass @Throws(VerifyException::class)
constructor(val annotatedElement: TypeElement, elementUtils: Elements) : CommonAnnotatedClass {
    val packageName: String?
    val clazzName: String
    var baseView: ClassName
    val baseViewName: String
        get() = baseView.simpleName
    var screen: ClassName
    val screenName: String
        get() = screen.simpleName
    var superClass: TypeName

    init {
        val packageElement = elementUtils.getPackageOf(annotatedElement)
        this.packageName = if (packageElement.isUnnamed) null else packageElement.qualifiedName.toString()
        this.clazzName = annotatedElement.simpleName.toString()

        val requireViews = annotatedElement.getAnnotation(RequireViews::class.java)
        this.baseView = getClass(requireViews)

        val requireScreen = annotatedElement.getAnnotation(RequireScreen::class.java)
        this.screen = getClass(requireScreen)

        this.superClass = ClassName("com.naver.android.svc.core.controltower", "ControlTower")
    }
}

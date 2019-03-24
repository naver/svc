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
 */package com.naver.android.svc.compiler.processor.screen

import com.google.common.base.VerifyException
import com.naver.android.svc.annotation.*
import com.naver.android.svc.compiler.processor.CommonAnnotatedClass
import com.naver.android.svc.compiler.processor.CommonClass
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

class CustomScreenAnnotatedClass @Throws(VerifyException::class)
constructor(val annotatedElement: TypeElement, elementUtils: Elements): CommonAnnotatedClass {
    val packageName: String?
    val clazzName: String

    var baseView: ClassName
    val baseViewName: String
        get() = baseView.simpleName

    var controlTower: ClassName
    val controlTowerName: String
        get() = controlTower.simpleName

    lateinit var superClass: TypeName

    init {
        val packageElement = elementUtils.getPackageOf(annotatedElement)
        this.packageName = if (packageElement.isUnnamed) null else packageElement.qualifiedName.toString()
        this.clazzName = annotatedElement.simpleName.toString()

        val requireViews = annotatedElement.getAnnotation(RequireViews::class.java)
        this.baseView = getValueClass(requireViews, CommonClass.Views)

        val requireControlTower = annotatedElement.getAnnotation(RequireControlTower::class.java)
        this.controlTower = getValueClass(requireControlTower, CommonClass.ControlTower)

        val svcActivity = annotatedElement.getAnnotation(SvcCustomActivity::class.java)
        val svcFragment = annotatedElement.getAnnotation(SvcCustomFragment::class.java)
        val svcDialogFragment = annotatedElement.getAnnotation(SvcCustomDialogFragment::class.java)

        when {
            svcActivity != null -> this.superClass = getValueClass(svcActivity)
            svcFragment != null -> this.superClass = getValueClass(svcFragment)
            svcDialogFragment != null -> {
                //svcDialogFragment
                val requireListener = annotatedElement.getAnnotation(RequireListener::class.java)
                val dialogListener = if (requireListener != null) {
                    getValueClass(requireListener)
                } else {
                    ClassName("kotlin", "Unit")
                }
                val svcDialogFragmentClassName = getValueClass(svcDialogFragment)
                this.superClass = svcDialogFragmentClassName.parameterizedBy(dialogListener)
            }
        }
    }
}

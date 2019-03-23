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
import com.naver.android.annotation.*
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

class ScreenAnnotatedClass @Throws(VerifyException::class)
constructor(val annotatedElement: TypeElement, elementUtils: Elements) {
    val packageName: String?
    val clazzName: String
    var baseView: ClassName
    var baseViewName: String
    var controlTower: ClassName
    var controlTowerName: String
    var dialogListener: ClassName? = null
    var dialogListenerName: String? = null
    var superClass: TypeName? = null

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

        val requireControlTower = annotatedElement.getAnnotation(RequireControlTower::class.java)
        val packageIndexS0 = requireControlTower.toString().lastIndexOf("=")
        val controlTowerPackage = requireControlTower
            .toString()
            .substring(packageIndexS0 + 1, requireControlTower.toString().length - 1)

        var indexCT = controlTowerPackage.lastIndexOf(".")
        if (indexCT == -1) indexCT = controlTowerPackage.lastIndexOf("\\.")

        this.controlTowerName = controlTowerPackage.substring(indexCT + 1)
        this.controlTower = ClassName.get(controlTowerPackage.substring(0, indexCT), controlTowerName)

        val svcActivity = annotatedElement.getAnnotation(SvcActivity::class.java)
        val svcFragment = annotatedElement.getAnnotation(SvcFragment::class.java)
        val svcDialogFragment = annotatedElement.getAnnotation(SvcDialogFragment::class.java)

        when {
            svcActivity != null -> this.superClass = ClassName.get("com.naver.android.svc.core.screen", "SvcActivity")
            svcFragment != null -> this.superClass = ClassName.get("com.naver.android.svc.core.screen", "SvcFragment")
            svcDialogFragment != null -> {
                //svcDialogFragment
                val requireListener = annotatedElement.getAnnotation(RequireListener::class.java)
                val indexOf0 = requireListener.toString().lastIndexOf("=")
                val listenerPackage = requireListener
                    .toString()
                    .substring(indexOf0 + 1, requireListener.toString().length - 1)

                var indexListener = listenerPackage.lastIndexOf(".")
                if (indexListener == -1) indexListener = listenerPackage.lastIndexOf("\\.")

                this.dialogListenerName = listenerPackage.substring(indexListener + 1)
                this.dialogListener = ClassName.get(listenerPackage.substring(0, indexListener), dialogListenerName)

                val svcDialogFragmentClassName = ClassName.get("com.naver.android.svc.core.screen", "SvcDialogFragment")
                this.superClass = ParameterizedTypeName.get(svcDialogFragmentClassName, dialogListener)
            }
        }
    }
}

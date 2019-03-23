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

import com.google.auto.service.AutoService
import com.google.common.base.VerifyException
import com.naver.android.svc.annotation.ControlTower
import com.naver.android.svc.annotation.SvcActivity
import com.naver.android.svc.annotation.SvcDialogFragment
import com.naver.android.svc.annotation.SvcFragment
import com.squareup.javapoet.JavaFile
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic.Kind.ERROR

@AutoService(Processor::class)
@SupportedOptions(SvcProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class SvcProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    private var messager: Messager? = null

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        this.messager = processingEnv.messager
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val supportedTypes = HashSet<String>()
        supportedTypes.add(ControlTower::class.java.canonicalName)
        supportedTypes.add(SvcFragment::class.java.canonicalName)
        supportedTypes.add(SvcDialogFragment::class.java.canonicalName)
        supportedTypes.add(SvcActivity::class.java.canonicalName)
        return supportedTypes
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun process(
        annotations: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        if (annotations.isEmpty()) {
            return true
        }

        roundEnvironment
            .getElementsAnnotatedWith(ControlTower::class.java)
            .stream()
            .map { annotatedType -> annotatedType as TypeElement }
            .forEach { annotatedType ->
                try {
                    checkIsClassType(
                        annotatedType,
                        "Only classes can be annotated with @ControlTower.")
                    processControlTower(annotatedType)
                } catch (e: IllegalAccessException) {
                    showProcessErrorLog(e.message, annotatedType)
                }
            }

        roundEnvironment
            .getElementsAnnotatedWith(SvcActivity::class.java)
            .stream()
            .map { annotatedType -> annotatedType as TypeElement }
            .forEach { annotatedType ->
                try {
                    checkIsClassType(
                        annotatedType,
                        "Only classes can be annotated with @SvcActivity.")
                    processScreen(annotatedType)
                } catch (e: IllegalAccessException) {
                    showProcessErrorLog(e.message, annotatedType)
                }
            }

        roundEnvironment
            .getElementsAnnotatedWith(SvcFragment::class.java)
            .stream()
            .map { annotatedType -> annotatedType as TypeElement }
            .forEach { annotatedType ->
                try {
                    checkIsClassType(
                        annotatedType,
                        "Only classes can be annotated with @SvcFragment.")
                    processScreen(annotatedType)
                } catch (e: IllegalAccessException) {
                    showProcessErrorLog(e.message, annotatedType)
                }
            }

        roundEnvironment
            .getElementsAnnotatedWith(SvcDialogFragment::class.java)
            .stream()
            .map { annotatedType -> annotatedType as TypeElement }
            .forEach { annotatedType ->
                try {
                    checkIsClassType(
                        annotatedType,
                        "Only classes can be annotated with @SvcDialogFragment.")
                    processScreen(annotatedType)
                } catch (e: IllegalAccessException) {
                    showProcessErrorLog(e.message, annotatedType)
                }
            }

        return true
    }

    private fun processControlTower(annotatedType: TypeElement) {
        try {
            val annotatedClazz = ControlTowerAnnotatedClass(annotatedType, processingEnv.elementUtils)
            val packageElement = processingEnv.elementUtils.getPackageOf(annotatedClazz.annotatedElement)
            val packageName = if (packageElement.isUnnamed)
                null
            else
                packageElement.qualifiedName.toString()
            generateProcessControlTower(packageName, annotatedClazz)
        } catch (e: VerifyException) {
            showProcessErrorLog(e.message, annotatedType)
        }

    }

    private fun generateProcessControlTower(
        packageName: String?, annotatedClazz: ControlTowerAnnotatedClass) {
        try {
            val controlTowerGenerator = ControlTowerGenerator(packageName!!, annotatedClazz)
            val controlTowerClazz = controlTowerGenerator.generate()
            JavaFile.builder(packageName, controlTowerClazz)
                .build()
                .writeTo(processingEnv.filer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun processScreen(annotatedType: TypeElement) {
        try {
            val annotatedClazz = ScreenAnnotatedClass(annotatedType, processingEnv.elementUtils)
            val packageElement = processingEnv.elementUtils.getPackageOf(annotatedClazz.annotatedElement)
            val packageName = if (packageElement.isUnnamed)
                null
            else
                packageElement.qualifiedName.toString()
            generateScreen(packageName, annotatedClazz)
        } catch (e: VerifyException) {
            showProcessErrorLog(e.message, annotatedType)
        }

    }

    private fun generateScreen(packageName: String?, annotatedClazz: ScreenAnnotatedClass) {
        try {
            val controlTowerGenerator = ScreenExtendsGenerator(packageName!!, annotatedClazz)
            val controlTowerClazz = controlTowerGenerator.generate()
            JavaFile.builder(packageName, controlTowerClazz)
                .build()
                .writeTo(processingEnv.filer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(IllegalAccessException::class)
    private fun checkIsClassType(annotatedType: TypeElement, errorMsg: String) {
        if (!annotatedType.kind.isClass) {
            throw IllegalAccessException(errorMsg)
        }
    }

    private fun showProcessErrorLog(message: String?, element: Element) {
        messager!!.printMessage(ERROR, message, element)
    }
}

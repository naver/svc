package com.naver.android.svc.compiler.processor.screen

import com.google.common.base.VerifyException
import com.naver.android.svc.annotation.SvcActivity
import com.naver.android.svc.annotation.SvcDialogFragment
import com.naver.android.svc.annotation.SvcFragment
import com.naver.android.svc.compiler.processor.CommonProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

/**
 * @author bs.nam@navercorp.com
 */
class ScreenProcessor(env: ProcessingEnvironment): CommonProcessor(env){

    fun processScreen(roundEnvironment: RoundEnvironment) {
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
        val generator = ScreenExtendsGenerator(packageName!!, annotatedClazz)
        val clazz = generator.generate()
        writeFile(packageName, generator, clazz)
    }
}
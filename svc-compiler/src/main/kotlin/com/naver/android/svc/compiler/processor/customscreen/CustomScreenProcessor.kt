package com.naver.android.svc.compiler.processor.screen

import com.google.common.base.VerifyException
import com.naver.android.svc.annotation.SvcCustomActivity
import com.naver.android.svc.annotation.SvcCustomDialogFragment
import com.naver.android.svc.annotation.SvcCustomFragment
import com.naver.android.svc.compiler.processor.CommonProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

/**
 * @author bs.nam@navercorp.com
 */
class CustomScreenProcessor(env: ProcessingEnvironment) : CommonProcessor(env) {

    fun processScreen(roundEnvironment: RoundEnvironment) {
        roundEnvironment
            .getElementsAnnotatedWith(SvcCustomActivity::class.java)
            .stream()
            .map { annotatedType -> annotatedType as TypeElement }
            .forEach { annotatedTypeElement ->
                try {
                    checkIsClassType(
                        annotatedTypeElement,
                        "Only classes can be annotated with @SvcCustomActivity.")

                    processScreen(annotatedTypeElement)
                } catch (e: IllegalAccessException) {
                    showProcessErrorLog(e.message, annotatedTypeElement)
                }
            }

        roundEnvironment
            .getElementsAnnotatedWith(SvcCustomFragment::class.java)
            .stream()
            .map { annotatedType -> annotatedType as TypeElement }
            .forEach { annotatedTypeElement ->
                try {
                    checkIsClassType(
                        annotatedTypeElement,
                        "Only classes can be annotated with @SvcCustomFragment.")
                    processScreen(annotatedTypeElement)
                } catch (e: IllegalAccessException) {
                    showProcessErrorLog(e.message, annotatedTypeElement)
                }
            }

        roundEnvironment
            .getElementsAnnotatedWith(SvcCustomDialogFragment::class.java)
            .stream()
            .map { annotatedType -> annotatedType as TypeElement }
            .forEach { annotatedTypeElement ->
                try {
                    checkIsClassType(
                        annotatedTypeElement,
                        "Only classes can be annotated with @SvcCustomDialogFragment.")
                    processScreen(annotatedTypeElement)
                } catch (e: IllegalAccessException) {
                    showProcessErrorLog(e.message, annotatedTypeElement)
                }
            }
    }


    private fun processScreen(annotatedTypeElement: TypeElement) {
        try {
            val annotatedClazz = CustomScreenAnnotatedClass(annotatedTypeElement, processingEnv.elementUtils)
            val packageElement = processingEnv.elementUtils.getPackageOf(annotatedClazz.annotatedElement)
            val packageName = if (packageElement.isUnnamed)
                null
            else
                packageElement.qualifiedName.toString()
            generateScreen(packageName, annotatedClazz)
        } catch (e: VerifyException) {
            showProcessErrorLog(e.message, annotatedTypeElement)
        }

    }

    private fun generateScreen(packageName: String?, annotatedClazz: CustomScreenAnnotatedClass) {
        val generator = CustomScreenExtendsGenerator(packageName!!, annotatedClazz)
        val clazz = generator.generate()
        writeFile(packageName, generator, clazz)
    }
}
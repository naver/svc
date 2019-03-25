package com.naver.android.svc.compiler.processor.controltower

import com.google.common.base.VerifyException
import com.naver.android.svc.annotation.ControlTower
import com.naver.android.svc.compiler.processor.CommonProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

/**
 * @author bs.nam@navercorp.com
 */
class ControlTowerProcessor(processingEnv: ProcessingEnvironment): CommonProcessor(processingEnv){

    fun processControlTower(roundEnvironment: RoundEnvironment) {
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
        val generator = ControlTowerGenerator(packageName!!, annotatedClazz)
        val clazz = generator.generate()
        writeFile(packageName, generator, clazz)
    }

}
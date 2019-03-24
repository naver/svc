package com.naver.android.svc.compiler.processor.controltower

import com.google.common.base.VerifyException
import com.naver.android.svc.annotation.ControlTower
import com.naver.android.svc.compiler.SvcProcessor
import com.naver.android.svc.compiler.processor.CommonProcessor
import com.squareup.kotlinpoet.FileSpec
import java.io.File
import java.io.IOException
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
        try {
            val controlTowerGenerator = ControlTowerGenerator(packageName!!, annotatedClazz)
            val controlTowerClazz = controlTowerGenerator.generate()

            val kaptKotlinGeneratedDir = processingEnv.options[SvcProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME]
            val file = File(kaptKotlinGeneratedDir, controlTowerGenerator.getControlTowerName() + ".kt")
            FileSpec.get(packageName, controlTowerClazz)
                .writeTo(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
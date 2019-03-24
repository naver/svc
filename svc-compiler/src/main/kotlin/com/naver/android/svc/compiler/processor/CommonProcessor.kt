package com.naver.android.svc.compiler.processor

import com.naver.android.svc.compiler.SvcProcessor
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

/**
 * @author bs.nam@navercorp.com
 */
abstract class CommonProcessor(val processingEnv: ProcessingEnvironment){

    private val messager = processingEnv.messager

    @Throws(IllegalAccessException::class)
    fun checkIsClassType(annotatedType: TypeElement, errorMsg: String) {
        if (!annotatedType.kind.isClass) {
            throw IllegalAccessException(errorMsg)
        }
    }

    fun showProcessErrorLog(message: String?, element: Element) {
       messager.printMessage(Diagnostic.Kind.ERROR, message, element)
    }

    fun writeFile(packageName: String, generator: CommonGenerator, clazz: TypeSpec) {
        try {
            val kaptKotlinGeneratedDir = processingEnv.options[SvcProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME]
            val file = File(kaptKotlinGeneratedDir, generator.extendsName + ".kt")
            FileSpec.get(packageName, clazz)
                .writeTo(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
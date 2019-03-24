package com.naver.android.svc.compiler.processor

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
}
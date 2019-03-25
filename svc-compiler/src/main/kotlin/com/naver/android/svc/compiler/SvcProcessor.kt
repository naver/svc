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
import com.naver.android.svc.annotation.*
import com.naver.android.svc.compiler.processor.controltower.ControlTowerProcessor
import com.naver.android.svc.compiler.processor.controltower.CustomControlTowerProcessor
import com.naver.android.svc.compiler.processor.screen.CustomScreenProcessor
import com.naver.android.svc.compiler.processor.screen.ScreenProcessor
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@SupportedOptions(SvcProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class SvcProcessor : AbstractProcessor() {

    lateinit var controlTowerProcessor : ControlTowerProcessor
    lateinit var customControlTowerProcessor : CustomControlTowerProcessor
    lateinit var customScreenProcessor : CustomScreenProcessor
    lateinit var screenProcessor : ScreenProcessor

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        controlTowerProcessor = ControlTowerProcessor(processingEnv)
        customControlTowerProcessor = CustomControlTowerProcessor(processingEnv)
        customScreenProcessor = CustomScreenProcessor(processingEnv)
        screenProcessor = ScreenProcessor(processingEnv)
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val supportedTypes = HashSet<String>()
        supportedTypes.add(ControlTower::class.java.canonicalName)
        supportedTypes.add(CustomControlTower::class.java.canonicalName)

        supportedTypes.add(SvcActivity::class.java.canonicalName)
        supportedTypes.add(SvcCustomActivity::class.java.canonicalName)

        supportedTypes.add(SvcFragment::class.java.canonicalName)
        supportedTypes.add(SvcCustomFragment::class.java.canonicalName)

        supportedTypes.add(SvcDialogFragment::class.java.canonicalName)
        supportedTypes.add(SvcCustomDialogFragment::class.java.canonicalName)
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

        controlTowerProcessor.processControlTower(roundEnvironment)
        customControlTowerProcessor.processControlTower(roundEnvironment)
        customScreenProcessor.processScreen(roundEnvironment)
        screenProcessor.processScreen(roundEnvironment)
        return true
    }
}

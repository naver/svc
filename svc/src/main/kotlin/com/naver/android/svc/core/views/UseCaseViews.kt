package com.naver.android.svc.core.views

import com.naver.android.svc.core.screen.SvcScreen

abstract class UseCaseViews<out Screen : SvcScreen<*, *>, U : UseCase>(screen: Screen) : SvcViews<Screen>(screen) {
    lateinit var useCase: U

    fun setControllerUsecase(useCase: UseCase) {
        try {
            @Suppress("UNCHECKED_CAST")
            this.useCase = useCase as U
        } catch (e: Exception) {
            throw IllegalStateException("useCase type dismatch \n ${useCase.javaClass.simpleName} are using wrong usecase")
        }
    }

}
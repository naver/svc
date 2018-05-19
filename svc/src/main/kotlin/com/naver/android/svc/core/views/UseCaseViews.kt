package com.naver.android.svc.core.views

import com.naver.android.svc.core.ActivityProvider

abstract class UseCaseViews<out Owner : ActivityProvider, U : UseCase>(owner: Owner) : SvcBaseViews<Owner>(owner) {
    lateinit var usecase: U

    fun setControllerUsecase(useCase: UseCase) {
        try {
            @Suppress("UNCHECKED_CAST")
            this.usecase = useCase as U
        } catch (e: Exception) {
            throw IllegalStateException("useCase type dismatch \n ${useCase.javaClass.simpleName} are using wrong usecase")
        }
    }

}
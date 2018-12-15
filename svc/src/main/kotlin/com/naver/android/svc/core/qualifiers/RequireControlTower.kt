package com.naver.android.svc.core.qualifiers

import com.naver.android.svc.core.controltower.ControlTower
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

/**
 * Developed by skydoves on 2017-12-15.
 * Copyright (c) 2017 skydoves rights reserved.
 */

@Inherited
annotation class RequireControlTower(val value: KClass<out ControlTower>)

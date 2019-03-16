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
 */
package com.naver.android.svc.core.controltower

import android.util.Log
import com.naver.android.svc.core.screen.Screen
import com.naver.android.svc.core.views.Views
import java.lang.reflect.InvocationTargetException
import java.util.HashMap
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * Developed by skydoves on 2018-12-15.
 * Copyright (c) 2018 skydoves rights reserved.
 */

@Suppress("unused", "UNCHECKED_CAST")
class ControlTowerManager {
  private val controlTowers = HashMap<String, ControlTower>()

  fun <T : ControlTower> fetch(
    screen: Screen<*>,
    controlTowerClass: KClass<*>,
    views: Views
  ): T {
    val id = fetchId(screen.javaClass)
    var activityControlTower: ControlTower? = this.controlTowers[id]

    if (controlTowerClass !is ControlTower) {
      RuntimeException("controlTower class(${controlTowerClass.simpleName}) should extends ControlTower class.")
    }

    if (activityControlTower == null) {
      activityControlTower = create(screen, controlTowerClass, views, id!!)
    }

    return activityControlTower as T
  }

  private fun create(
    screen: Screen<*>,
    ControlTowerClass: KClass<*>,
    views: Views,
    id: String
  ): ControlTower {

    val activityControlTower: ControlTower

    try {
      activityControlTower = ControlTowerClass.createInstance() as ControlTower
      Log.e("Test", "${fetchId(screen.javaClass)} created")
    } catch (exception: ClassCastException) {
      throw RuntimeException(exception)
    } catch (exception: IllegalAccessException) {
      throw RuntimeException(exception)
    } catch (exception: InvocationTargetException) {
      throw RuntimeException(exception)
    } catch (exception: InstantiationException) {
      throw RuntimeException(exception)
    } catch (exception: NoSuchMethodException) {
      throw RuntimeException(exception)
    }

    this.controlTowers[id] = activityControlTower
    activityControlTower.onCreateControlTower(screen, views)
    return activityControlTower
  }

  fun destroy(activityControlTower: ControlTower) {
    activityControlTower.onDestroy()

    val iterator = this.controlTowers.entries.iterator()
    while (iterator.hasNext()) {
      val entry = iterator.next()
      if (activityControlTower == entry.value) {
        iterator.remove()
        Log.e("Test", "${entry.key} destroyed")
      }
    }
  }

  private fun fetchId(modelClass: Class<*>): String? {
    return "$ControlTower_ID_KEY:${getCanonicalName(modelClass)}"
  }

  private fun findIdForControlTower(activityControlTower: ControlTower): String {
    for ((key, value) in this.controlTowers) {
      if (activityControlTower == value) {
        return key
      }
    }

    throw RuntimeException("cannot find ControlTower in map!")
  }

  private fun getCanonicalName(clazz: Class<*>): String {
    return clazz.canonicalName ?: UUID.randomUUID().toString()
  }

  companion object {
    private const val ControlTower_ID_KEY = "ControlTower_id"

    val instance = ControlTowerManager()
  }
}

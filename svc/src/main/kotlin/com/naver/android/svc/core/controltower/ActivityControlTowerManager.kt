package com.naver.android.svc.core.controltower

import android.util.Log
import com.naver.android.svc.core.screen.SvcActivity
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
class ActivityControlTowerManager {
    private val controlTowers = HashMap<String, ControlTower>()

    fun <T : ControlTower> fetch(activity: SvcActivity<*>,
                                 controlTowerClass: KClass<T>,
                                 views: Views): T {
        val id = fetchId(activity.javaClass)
        var activityControlTower: ControlTower? = this.controlTowers[id]

        if (activityControlTower == null) {
            activityControlTower = create(activity, controlTowerClass, views, id!!)
        }

        return activityControlTower as T
    }

    private fun <T : ControlTower> create(activity: SvcActivity<*>, ControlTowerClass: KClass<T>, views: Views,
                                          id: String): ControlTower {

        val activityControlTower: ControlTower

        try {
            activityControlTower = ControlTowerClass.createInstance()
            Log.e("Test", "${fetchId(activity.javaClass)} created")
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
        activityControlTower.onCreateControlTower(activity, views)
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

        val instance = ActivityControlTowerManager()
    }
}
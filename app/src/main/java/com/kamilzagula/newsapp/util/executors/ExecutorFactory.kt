package com.kamilzagula.newsapp.util.executors

import java.util.concurrent.Executor

class ExecutorFactory {
    inline fun <reified T : Executor> createExecutor(): Executor = when (T::class) {
        BackgroundThreadExecutor::class -> BackgroundThreadExecutor()
        UiThreadExecutor::class -> UiThreadExecutor()
        else -> throw IllegalArgumentException("Unknown executor type")
    }
}
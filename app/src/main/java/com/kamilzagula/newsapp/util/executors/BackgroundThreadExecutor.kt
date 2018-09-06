package com.kamilzagula.newsapp.util.executors

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BackgroundThreadExecutor : Executor {
    private val executor = Executors.newFixedThreadPool(3)

    override fun execute(command: Runnable?) {
        executor.execute(command)
    }
}
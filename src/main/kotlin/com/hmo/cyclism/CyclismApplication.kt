package com.hmo.cyclism

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class CyclismApplication

fun main(args: Array<String>) {
	runApplication<CyclismApplication>(*args)
}

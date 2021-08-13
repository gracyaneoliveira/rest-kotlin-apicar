package com.car.apicar.infrastructure

import org.springframework.core.io.ClassPathResource

fun loadFileContents(fileName: String) =
    ClassPathResource(fileName).inputStream.readAllBytes().toString(Charsets.UTF_8)
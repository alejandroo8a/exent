package com.example.alien.excent.network.adapters

import com.squareup.moshi.JsonQualifier

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@JsonQualifier
annotation class NullToEmptyString

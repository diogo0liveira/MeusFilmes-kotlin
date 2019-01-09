package com.diogo.oliveira.mymovies.util

import org.mockito.Mockito

fun <T> eq(obj: T): T = Mockito.eq<T>(obj)

fun <T> any(): T = Mockito.any<T>()

//fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
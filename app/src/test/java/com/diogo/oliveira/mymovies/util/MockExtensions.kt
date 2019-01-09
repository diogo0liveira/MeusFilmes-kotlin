package com.diogo.oliveira.mymovies.util

import org.mockito.Mockito

@Suppress("HasPlatformType")
inline fun <reified T : Any> mock() = Mockito.mock(T::class.java)
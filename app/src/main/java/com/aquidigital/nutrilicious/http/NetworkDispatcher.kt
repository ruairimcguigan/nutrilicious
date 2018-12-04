package com.aquidigital.nutrilicious.http

import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext


@UseExperimental(ObsoleteCoroutinesApi::class)
val NETWORK = newFixedThreadPoolContext(2, "NETWORK")  // Dedicated network context

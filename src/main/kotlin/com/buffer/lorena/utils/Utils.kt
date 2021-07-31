package com.buffer.lorena.utils

import java.util.*

val <T> Optional<T>.orNull: T? get() = if (this.isPresent) this.get() else null
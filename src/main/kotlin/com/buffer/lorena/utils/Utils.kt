package com.buffer.lorena.utils

import java.util.*

inline val <T> Optional<T>.orNull: T? get() = if (this.isPresent) this.get() else null
package com.huaqing.property.ext.arrow

import arrow.core.Option

inline fun <T> Option<T>.whenNotNull(consumer: (T) -> Unit) =
    fold({}, consumer)

inline fun <T> Option<T>.whenEmpty(action: () -> Unit) =
    fold(action, {})
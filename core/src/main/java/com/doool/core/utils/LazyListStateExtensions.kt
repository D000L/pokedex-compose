package com.doool.core.utils

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.getItemTopOffset(index: Int = 0): Int {
    val topOffset = layoutInfo.visibleItemsInfo.getOrNull(index)?.offset ?: 0
    val startOffset = layoutInfo.viewportStartOffset
    return topOffset - startOffset
}

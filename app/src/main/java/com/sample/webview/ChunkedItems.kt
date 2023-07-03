package com.sample.webview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

inline fun <T> LazyGridScope.chunkedItems(
    items: List<T>,
    chunkSize: Int,
    chunkedContentPadding: PaddingValues = PaddingValues(0.dp),
    rowSpace: Dp = 0.dp,
    noinline key: ((Int) -> Any)? = null,
    noinline span: (LazyGridItemSpanScope.(Int) -> GridItemSpan)? = null,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T) -> Unit,
) {
    val chunkedList = items.chunked(chunkSize)
    items(chunkedList.size) { chunkIndex ->
        val chunkedItems = chunkedList[chunkIndex]
        Row(
            Modifier
                .fillMaxWidth()
                .padding(chunkedContentPadding),
        ) {
            chunkedItems.forEachIndexed { index, item ->
                Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    itemContent(item)
                }
                if (index < chunkedItems.lastIndex || chunkedItems.size < chunkSize) {
                    Spacer(modifier = Modifier.size(rowSpace))
                }
            }
            val spacerLastIndex = chunkSize - chunkedItems.size - 1
            repeat(chunkSize - chunkedItems.size) { index ->
                Box(modifier = Modifier.fillMaxWidth().weight(1f))
                if (index < spacerLastIndex) {
                    Spacer(modifier = Modifier.size(rowSpace))
                }
            }
        }
    }
}

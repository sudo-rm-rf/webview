package com.sample.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = "Top",
                    style = TextStyle(
                        fontSize = 36.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                )
                Log.d("scrollState", "value:" + scrollState.value)
//                MyWebClient(url = "https://topics.smt.docomo.ne.jp/article/moneypost/life/moneypost-1031978?fm=topics&fm_topics_id=9599b100f7560617fede5fbce68c92d1")
                ChunkedItemsSample()

                Text(
                    text = "Bottom",
                    style = TextStyle(
                        fontSize = 36.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Composable
    fun MyWebClient(url: String) {
        val backPressed = remember { mutableStateOf(false) }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                }
            },
            update = { webView ->
                if (backPressed.value) {
                    backPressed.value = false
                    if (webView.canGoBack()) {
                        webView.goBack()
                    } else {
                        finish()
                    }
                }
            },
        )

        BackHandler(
            enabled = true,
            onBack = {
                backPressed.value = true
            },
        )
    }
}

private const val GRID_COLUMN_SIZE = 1

@Composable
private fun ChunkedItemsSample(
    items: List<String> = buildList { repeat(3) { add("item$it") } }
) {
    Box(Modifier
        .fillMaxWidth()
        .height(500.dp)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxHeight(),
            columns = GridCells.Fixed(count = GRID_COLUMN_SIZE)
        ) {
            item(span = { GridItemSpan(GRID_COLUMN_SIZE) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Header",
                        color = Color.White,
                    )
                }
            }
            chunkedItems(
                items = items,
                chunkSize = GRID_COLUMN_SIZE,
                span = { GridItemSpan(GRID_COLUMN_SIZE) },
                rowSpace = 8.dp,
                chunkedContentPadding = PaddingValues(top = 8.dp, start = 8.dp, end = 8.dp)
            ) { item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.LightGray)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null
                    )
                    Text(text = item)
                }
            }

            item(span = { GridItemSpan(GRID_COLUMN_SIZE) }) { Spacer(modifier = Modifier.size(8.dp)) }
            item(span = { GridItemSpan(GRID_COLUMN_SIZE) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Footer",
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChunkedItemsSamplePreview() {
    ChunkedItemsSample()
}

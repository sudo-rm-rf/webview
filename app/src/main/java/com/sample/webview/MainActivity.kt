package com.sample.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
                Log.d("scrollState", "value:"+ scrollState.value)
                MyWebClient(url = "https://topics.smt.docomo.ne.jp/article/moneypost/life/moneypost-1031978?fm=topics&fm_topics_id=9599b100f7560617fede5fbce68c92d1")
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

package com.leromaro.sistemapacientes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun Banner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Gray) // Set your desired background color
            .wrapContentHeight(align = Alignment.Bottom)
    ) {
        AndroidView(
            // Specifying the width for ads
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                // Creating an AdView instance
                AdView(context).apply {
                    // Setting ad size
                    setAdSize(AdSize.BANNER)
                    // Setting ad unit id
                    adUnitId = "ca-app-pub-3940256099942544/6300978111" // Test ad unit id
                    // Loading the ad
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}


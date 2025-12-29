package com.universall.watertracker.main.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GenericScrollablePage(
    modifier: Modifier = Modifier,
    layoutPadding: PaddingValues,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(bottom = layoutPadding.calculateBottomPadding())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()
            }
        }
    }
}
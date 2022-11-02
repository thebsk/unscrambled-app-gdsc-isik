package com.example.android.unscramble.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.unscramble.R
import com.example.android.unscramble.data.Analytics

@Composable
fun AnalyticsScreen(
    analyticsViewModel: AnalyticsViewModel = viewModel()
) {
    val uiState by analyticsViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Analytics Screen")
                }
            )
        }
    ) { paddings ->
        LazyColumn(
            modifier = Modifier.padding(paddings),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.analyticsData) { singleData ->
                AnalyticItem(analytics = singleData)
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnalyticItem(analytics: Analytics) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colors.secondary else MaterialTheme.colors.primaryVariant,
    )
    Card(
        onClick = { expanded = !expanded },
        backgroundColor = color,
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(22.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
        ) {
            Text(
                text = "${analytics.characterCount}-char words",
                modifier = Modifier.weight(1f)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                if (expanded) {
                    analytics.occurrences.forEach {
                        Text(text = it)
                    }
                } else {
                    analytics.occurrences.take(2).forEach {
                        Text(text = it)
                    }
                }
            }
        }
    }
}
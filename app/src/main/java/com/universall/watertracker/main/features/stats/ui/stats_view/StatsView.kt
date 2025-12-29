package com.universall.watertracker.main.features.stats.ui.stats_view

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.universall.watertracker.core.weekBounds
import com.universall.watertracker.main.common.db.AppDatabase
import com.universall.watertracker.main.common.ui.GenericScrollablePage
import com.universall.watertracker.main.features.settings.data.repositories.SettingsRepositoryImpl
import com.universall.watertracker.main.features.settings.domain.services_impl.SettingsServiceImpl
import com.universall.watertracker.main.features.stats.data.repositories.StatsRepositoryImpl
import com.universall.watertracker.main.features.stats.domain.services_impl.StatsServiceImpl
import com.universall.watertracker.main.features.stats.ui.stats_view.components.DayRecordsSelection
import com.universall.watertracker.main.features.stats.ui.stats_view.components.GraphSelection
import java.time.LocalDate

@Composable
fun StatsView(
    viewModel: StatsViewModel,
    layoutPadding: PaddingValues
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(
        initialPage = LocalDate.now().dayOfWeek.value - 1,
        pageCount = { 7 }
    )

    // Sync UI -> Pager
    LaunchedEffect(uiState) {
        snapshotFlow { uiState.selectedDay }.collect { day ->
            val targetPage = day.dayOfWeek.value - 1
            if (pagerState.currentPage != targetPage) pagerState.animateScrollToPage(targetPage)
        }
    }

    // Sync Pager -> UI
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            val targetDay = uiState.selectedDay.dayOfWeek.value - 1
            val (weekStart, _) = LocalDate.now().weekBounds()
            if (targetDay != page) viewModel.setSelectedDate(weekStart.toLocalDate().plusDays(page.toLong()))
        }
    }

    GenericScrollablePage(
        layoutPadding = layoutPadding
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 32.dp, horizontal = 48.dp),
            text = "History",
            color = colors.onBackground,
            style = typography.headlineLarge
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GraphSelection(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                isLoading = uiState.isLoading,
                dailyGoal = uiState.dailyGoal,
                waterMeasureUnit = uiState.waterMeasureUnit,
                selectedDay = uiState.selectedDay,
                weekStats = uiState.weekStats,
                setSelectedDay = { date ->
                    viewModel.setSelectedDate(date = date)
                }
            )

            HorizontalPager(
                userScrollEnabled = true,
                state = pagerState,
                modifier = Modifier
                    .wrapContentHeight()
                    .animateContentSize(),
                verticalAlignment = Alignment.Top,
            ) { page ->
                DayRecordsSelection(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    isLoading = uiState.isLoading,
                    selectedDay = uiState.selectedDay,
                    selectedDayStats = uiState.weekStats?.let { it.daysStats[page] }
                )
            }
        }
    }
}


@Composable
fun StatsView(
    context: Context,
    layoutPadding: PaddingValues
) {
    val settingsService = remember {
        SettingsServiceImpl(
            repository = SettingsRepositoryImpl(context = context)
        )
    }

    val factory = remember {
        StatsViewModelFactory(
            statsService = StatsServiceImpl(
                repository = StatsRepositoryImpl(
                    dao = AppDatabase.getInstance(context).waterIntakeDao()
                )
            ),
            settingsService = settingsService
        )
    }

    StatsView(
        viewModel = viewModel(factory = factory),
        layoutPadding = layoutPadding
    )
}

package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun HomeScreen() {
    var selectedEvent by remember { mutableStateOf<CommunityEvent?>(null) }
    val listState = rememberLazyListState()

    val scrollPercentage by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo

            if (visibleItemsInfo.isEmpty()) {
                0f
            } else {
                val firstVisibleItem = visibleItemsInfo.first()
                val firstItemOffset = firstVisibleItem.offset
                val firstItemIndex = firstVisibleItem.index

                val totalItemsCount = layoutInfo.totalItemsCount
                val viewportHeight = layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset

                val averageItemHeight = if (visibleItemsInfo.isNotEmpty()) {
                    visibleItemsInfo.sumOf { it.size } / visibleItemsInfo.size
                } else {
                    1
                }

                val totalContentHeight = totalItemsCount * averageItemHeight
                val maxScrollOffset = (totalContentHeight - viewportHeight).coerceAtLeast(1)

                val currentScrollOffset = (firstItemIndex * averageItemHeight - firstItemOffset).coerceAtLeast(0)

                (currentScrollOffset.toFloat() / maxScrollOffset).coerceIn(0f, 1f)
            }
        }
    }

    val showScrollbar by remember {
        derivedStateOf {
            listState.layoutInfo.totalItemsCount > 0 &&
                    (listState.canScrollForward || listState.canScrollBackward)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(LightLavender)
        ) {
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = "Home",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Community Events",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = MediumGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            items(sampleEvents) { event ->
                EventCard(
                    event = event,
                    onClick = { selectedEvent = event },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }
        }

        if (showScrollbar) {
            val trackHeight = 300f
            val thumbHeight = 80f

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
                    .width(4.dp)
                    .height(trackHeight.dp)
                    .background(Color.White.copy(alpha = 0.4f), RoundedCornerShape(2.dp))
            ) {
                Box(
                    modifier = Modifier
                        .height(thumbHeight.dp)
                        .fillMaxWidth()
                        .offset(y = ((trackHeight - thumbHeight) * scrollPercentage).dp)
                        .background(DarkBlueGray, RoundedCornerShape(2.dp))
                )
            }
        }

        selectedEvent?.let { event ->
            EventScheduleDialog(
                event = event,
                onDismiss = { selectedEvent = null }
            )
        }
    }
}

@Composable
fun EventCard(
    event: CommunityEvent,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = event.timeRange,
                    fontSize = 11.sp,
                    color = MediumGray,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = event.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy,
                    maxLines = 1
                )
            }

            Text(
                text = event.description,
                fontSize = 13.sp,
                color = DeepNavy.copy(alpha = 0.6f),
                lineHeight = 18.sp,
                maxLines = 2
            )
        }
    }
}

@Composable
fun EventScheduleDialog(
    event: CommunityEvent,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = event.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = DeepNavy
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Available Schedules",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DeepNavy
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    event.schedules.forEach { schedule ->
                        ScheduleItem(schedule)
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleItem(schedule: EventSchedule) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = LightLavender
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = schedule.date,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = DeepNavy
                )

                Text(
                    text = schedule.time,
                    fontSize = 12.sp,
                    color = MediumGray
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = schedule.venue,
                fontSize = 12.sp,
                color = DeepNavy.copy(alpha = 0.7f)
            )
        }
    }
}

package com.specialtech.diary.ui.features.money.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.specialtech.diary.R
import com.specialtech.diary.data.datasources.money.local.LocalMoneyData
import com.specialtech.diary.data.datasources.money.models.MoneyData
import com.specialtech.diary.data.datasources.money.models.MoneyItemData
import com.specialtech.diary.ui.theme.MainDark
import com.specialtech.diary.ui.theme.MainOrange

@Preview
@Composable
fun MoneyRates(
    isVisible: Boolean = true,
    moneyData: Pair<MoneyData, MoneyData> = Pair(LocalMoneyData().moneyData1, LocalMoneyData().moneyData2)
) {
    val moneyRates: MutableList<Pair<MoneyItemData, MoneyItemData>> = mutableListOf()
    moneyData.first.moneyRates.forEach { firstRate ->
        val secondRate = moneyData.second.moneyRates.firstOrNull{ firstRate.name == it.name }
        if (secondRate != null)
            moneyRates.add(Pair(firstRate, secondRate))
    }
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "Курсы валют по состоянию на:\n ${moneyData.first.date}",
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                items(moneyRates) { rates ->
                    MoneyRateItem(rate1 = rates.first, rate2 = rates.second)
                }
            }
        }
    }
}

@Composable
fun MoneyRateItem(rate1: MoneyItemData, rate2: MoneyItemData) {
    var iconColor = Color.White
    val iconId = if (rate2.rate > rate1.rate) {
        iconColor = Color.Green
        R.drawable.ic_trending_up
    } else if (rate2.rate < rate1.rate) {
        iconColor = Color.Red
        R.drawable.ic_trending_down
    } else {
        R.drawable.ic_equal
    }
    Card(
        colors = CardColors(contentColor = MainDark, containerColor = MainOrange,
            disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp, pressedElevation = 5.dp, focusedElevation = 10.dp,
            hoveredElevation = 10.dp, draggedElevation = 10.dp, disabledElevation = 10.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Money name: ${rate1.name}",
                fontSize = 18.sp,
                color = Color.White
            )
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Yesterday: ${rate1.rate}",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(
                    painter = painterResource(iconId),
                    tint = iconColor,
                    contentDescription = null
                )
                Text(
                    text = "Today: ${rate2.rate}",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Text(
                text = "Description: ${rate1.description}",
                fontSize = 14.sp,
                color = Color.White
            )
        }

    }
}
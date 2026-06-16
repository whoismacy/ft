package com.shrmrm.ft.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.shrmrm.ft.data.domain.Expense

enum class Colors(
    val color: String,
) {
    YELLOW("#ffd966"),
    ORANGE("#fe8f88"),
    TEAL("#aacdba"),
    PURPLE("#baaff5"),
}

fun parseColor(hex: String): Color = Color(hex.toColorInt())

@Composable
fun BentoBox(
    title: String,
    expenses: List<Expense>,
    modifier: Modifier = Modifier,
) {
    val highest = expenses.minByOrNull { it.amount }!!.amount
    val sumIn = expenses.filter { it.amount > 0 }.sumOf { it.amount }
    val sumOut = expenses.filter { it.amount < 0 }.sumOf { it.amount }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(title, style = MaterialTheme.typography.titleLargeEmphasized)
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            BentoCard(
                title = "Highest Spent",
                value = "$highest",
                color = Colors.YELLOW,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(160.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                BentoCard(
                    title = "Money In",
                    value = "$sumIn",
                    color = Colors.PURPLE,
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(160.dp),
                )
                BentoCard(
                    title = "Money Out",
                    value = "$sumOut",
                    color = Colors.ORANGE,
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(160.dp),
                )
            }
            BentoCard(
                title = "Total Expenses",
                value = "${expenses.size}",
                color = Colors.TEAL,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(160.dp),
            )
        }
    }
}

@Composable
fun BentoCard(
    title: String,
    value: String,
    color: Colors,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = parseColor(color.color)),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
                fontSize = 12.sp,
                letterSpacing = 1.6.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

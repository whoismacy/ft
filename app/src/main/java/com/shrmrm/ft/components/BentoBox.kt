package com.shrmrm.ft.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shrmrm.ft.R
import com.shrmrm.ft.data.domain.Expense

@Composable
fun BentoBox(
    title: String,
    expenses: List<Expense>,
    modifier: Modifier = Modifier,
) {
    val totalSpent = expenses.filter { it.amount < 0 }.sumOf { it.amount.toDouble() }
    val totalIncome = expenses.filter { it.amount > 0 }.sumOf { it.amount.toDouble() }
    val balance = totalIncome + totalSpent

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(240.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            BentoCard(
                modifier =
                    Modifier
                        .weight(1.2f)
                        .fillMaxHeight(),
                title = "Current Balance",
                value = "KShs ${balance.toInt()}",
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                icon = R.drawable.baseline_account_balance_wallet_24,
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                BentoCard(
                    modifier = Modifier.weight(1f),
                    title = "Income",
                    value = "+${totalIncome.toInt()}",
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    icon = R.drawable.outline_trending_up_24,
                )
                BentoCard(
                    modifier = Modifier.weight(1f),
                    title = "Expenses",
                    value = "${totalSpent.toInt()}",
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    icon = R.drawable.baseline_trending_down_24,
                )
            }
        }

        BentoCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(100.dp),
            title = "All Transactions",
            value = "${expenses.size} transaction in this period",
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            icon = R.drawable.baseline_bar_chart_24,
        )
    }
}

@Composable
fun BentoCard(
    title: String,
    value: String,
    icon: Int,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(24.dp), // Softer corners
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = contentColor.copy(alpha = 0.8f),
                modifier = Modifier.size(24.dp),
            )
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = contentColor,
                )
                Text(
                    text = title.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = contentColor.copy(alpha = 0.6f),
                    letterSpacing = 1.sp,
                )
            }
        }
    }
}

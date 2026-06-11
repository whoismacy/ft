package com.shrmrm.ft.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shrmrm.ft.data.domain.Expense

@Composable
fun SingleExpense(
    expense: Expense,
    modifier: Modifier = Modifier,
) {
    Card(
        colors =
            CardDefaults
                .cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation =
            CardDefaults
                .cardElevation(1.dp),
        border =
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .clip(RoundedCornerShape(8.dp)),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val expensePositive = expense.amount > 0
            Text(text = expense.name, overflow = TextOverflow.Clip)
            Text(
                if (expensePositive) "${expense.amount}" else "-${expense.amount}",
                color = if (expensePositive) Color.Green else Color.Red,
                style = MaterialTheme.typography.bodyMediumEmphasized,
            )
        }
    }
}

package com.shrmrm.ft.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
fun SingleExpense(expense: Expense) {
    Card(
        colors =
            CardDefaults
                .cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border =
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f),
            ),
        modifier =
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(1.dp)),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val expensePositive = expense.amount > 0
            Text(
                text = expense.name,
                maxLines = 1,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMediumEmphasized,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                "KShs. ${expense.amount}",
                color =
                    if (expensePositive) {
                        MaterialTheme.colorScheme.tertiary
                    } else {
                        Color.Red
                    },
                style = MaterialTheme.typography.bodyLargeEmphasized,
            )
        }
    }
}

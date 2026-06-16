package com.shrmrm.ft.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shrmrm.ft.R
import com.shrmrm.ft.data.domain.Expense
import com.shrmrm.ft.data.viewmodels.FtIntent
import com.shrmrm.ft.data.viewmodels.FtViewModel
import kotlinx.coroutines.launch

@Composable
fun SingleExpense(
    expense: Expense,
    viewModel: FtViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val swipeToDismissBoxState =
        rememberSwipeToDismissBoxState(
            positionalThreshold = SwipeToDismissBoxDefaults.positionalThreshold,
        )
    SwipeToDismissBox(
        modifier = Modifier.fillMaxSize(),
        state = swipeToDismissBoxState,
        onDismiss = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.EndToStart -> {
                    coroutineScope.launch { swipeToDismissBoxState.reset() }
                    viewModel.handleIntent(FtIntent.DeleteExpense(expense.expenseId))
                }

                else -> {
                    coroutineScope.launch {
                        swipeToDismissBoxState.reset()
                    }
                }
            }
        },
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        painterResource(R.drawable.baseline_delete_forever_24),
                        contentDescription = null,
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(
                                    lerp(
                                        Color.LightGray,
                                        Color.Red,
                                        swipeToDismissBoxState.progress,
                                    ),
                                ).wrapContentSize(Alignment.CenterStart)
                                .padding(12.dp),
                        tint = Color.White,
                    )
                }

                else -> {}
            }
        },
    ) {
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
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.ExtraLight,
                        ),
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    if (!expensePositive) "- KShs ${expense.amount * -1}" else "+ KShs ${expense.amount}",
                    color =
                        if (expensePositive) {
                            MaterialTheme.colorScheme.tertiary
                        } else {
                            Color.Red
                        },
                    style =
                        MaterialTheme
                            .typography.titleLargeEmphasized
                            .copy(
                                fontWeight =
                                    FontWeight.ExtraBold,
                                letterSpacing = 1.5.sp,
                            ),
                )
            }
        }
    }
}

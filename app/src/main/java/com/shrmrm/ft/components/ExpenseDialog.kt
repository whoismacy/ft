package com.shrmrm.ft.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.shrmrm.ft.R
import com.shrmrm.ft.data.events.EventManager
import com.shrmrm.ft.data.viewmodels.FtIntent
import com.shrmrm.ft.data.viewmodels.FtViewModel

@Composable
fun ExpenseDialog(
    viewModel: FtViewModel,
    onDismissRequest: () -> Unit = {},
) {
    var expensePurpose by rememberSaveable { mutableStateOf("") }
    var expenseAmount by rememberSaveable { mutableStateOf("") }

    var isExpense by rememberSaveable { mutableStateOf(true) }

    fun createExpense() {
        val amountValue = expenseAmount.toIntOrNull()
        if (expensePurpose.isBlank() || amountValue == null) {
            EventManager.triggerEvent(
                EventManager
                    .AppEvent
                    .ShowSnackbar("Error: Input a valid purpose and amount"),
            )
        } else {
            val value = if (isExpense) -amountValue else amountValue
            viewModel.handleIntent(
                FtIntent.CreateExpense(
                    name = expensePurpose,
                    amount = value,
                ),
            )
            onDismissRequest()
        }
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.9f).wrapContentHeight(),
            color = MaterialTheme.colorScheme.surfaceContainerHigh,
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                Text(
                    "New Expense Transaction",
                    style = MaterialTheme.typography.headlineSmall,
                )

                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    SegmentedButton(
                        selected = !isExpense,
                        onClick = {
                            isExpense = false
                            expensePurpose = "Deposit"
                        },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                        label = { Text("Income") },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.outline_add_24),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        colors =
                            SegmentedButtonColors(
                                activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                activeContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                activeBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                                inactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                inactiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                inactiveBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f),
                                disabledActiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledActiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledActiveBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f),
                                disabledInactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledInactiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledInactiveBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f),
                            ),
                    )

                    SegmentedButton(
                        selected = isExpense,
                        onClick = {
                            isExpense = true
                            expensePurpose = ""
                        },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                        label = { Text("Expense") },
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.outline_check_indeterminate_small_24),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                            )
                        },
                        colors =
                            SegmentedButtonColors(
                                activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                activeContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                activeBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.4f),
                                inactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                inactiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                inactiveBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f),
                                disabledActiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledActiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledActiveBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f),
                                disabledInactiveContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                disabledInactiveContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                disabledInactiveBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.4f),
                            ),
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ExpenseInputField(
                        label = "Purpose",
                        value = expensePurpose,
                        onValueChange = { expensePurpose = it },
                        keyboardOptions =
                            KeyboardOptions(
                                capitalization = KeyboardCapitalization.Sentences,
                                keyboardType = KeyboardType.Text,
                            ),
                        icon = R.drawable.outline_description_24,
                    )

                    ExpenseInputField(
                        label = "Amount",
                        value = expenseAmount,
                        onValueChange = { if (it.all { c -> c.isDigit() }) expenseAmount = it },
                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                        icon = R.drawable.baseline_attach_money_24,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    TextButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { createExpense() },
                        modifier = Modifier.weight(1.5f),
                        shape = MaterialTheme.shapes.large,
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: Int,
    keyboardOptions: KeyboardOptions,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        painter = painterResource(R.drawable.outline_close_24),
                        contentDescription = "Clear",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
    )
}

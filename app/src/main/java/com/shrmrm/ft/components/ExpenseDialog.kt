package com.shrmrm.ft.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.shrmrm.ft.data.viewmodels.FtViewModel

@Composable
fun ExpenseDialog(
    viewModel: FtViewModel,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
) {
    var expensePurpose by rememberSaveable { mutableStateOf("") }
    var expenseAmount by rememberSaveable { mutableStateOf("") }
    var expenseTypeSpent by rememberSaveable { mutableStateOf(true) }

    val onExpensePurpose: (String) -> Unit = { expensePurpose = it }
    val onExpenseAmount: (String) -> Unit = { expenseAmount = it }

    fun createExpense() {
        val value =
            if (expenseTypeSpent) {
                expenseAmount.toInt() * -1
            } else {
                expenseAmount.toInt()
            }
        viewModel.createNewExpense(expensePurpose, value)
    }

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            shape = MaterialTheme.shapes.large,
        ) {
            Column(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    "Create New Expense",
                    style = MaterialTheme.typography.headlineMediumEmphasized,
                )
                Spacer(Modifier.height(4.dp))
                ExpenseInput(
                    label = "Purpose",
                    placeholder = "Input expense purpose",
                    value = expensePurpose,
                    onValueChange = onExpensePurpose,
                    isValueInt = false,
                )

                ExpenseInput(
                    label = "Amount",
                    placeholder = "Input expense amount",
                    value = expenseAmount,
                    onValueChange = onExpenseAmount,
                    isValueInt = true,
                )
                ExpenseType(expenseTypeSpent) { expenseTypeSpent = it }
                Spacer(Modifier.height(8.dp))
                ElevatedButton(
                    onClick = {
                        createExpense()
                        onDismissRequest()
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Create")
                }
            }
        }
    }
}

@Composable
fun ExpenseInput(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isValueInt: Boolean,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { value: String -> onValueChange(value) },
        enabled = true,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = false,
        minLines = 2,
        maxLines = 5,
        shape = MaterialTheme.shapes.medium,
        keyboardOptions =
            if (isValueInt) {
                KeyboardOptions(
                    keyboardType = KeyboardType.NumberSigned,
                )
            } else {
                KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Text,
                )
            },
    )
}

@Composable
fun ExpenseType(
    spendType: Boolean,
    changeExpenseType: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = !spendType,
                onCheckedChange = { changeExpenseType(false) },
            )
            Text("Add")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = spendType,
                onCheckedChange = { changeExpenseType(true) },
            )
            Text("Spent")
        }
    }
}

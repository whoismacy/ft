package com.shrmrm.ft.data.domain

import com.shrmrm.ft.R

enum class TaskState(
    val status: String,
    val icon: Int,
) {
    DONE(
        "DONE",
        R.drawable.outline_check_24,
    ),
    FAILED(
        "FAILED",
        R.drawable.outline_close_24,
    ),
    HOLD(
        "HOLD",
        R.drawable.outline_check_indeterminate_small_24,
    ),
}

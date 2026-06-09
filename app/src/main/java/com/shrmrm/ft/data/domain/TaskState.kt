package com.shrmrm.ft.data.domain

enum class TaskState(
    val status: String,
) {
    DONE("DONE"),
    UNATTENDED("UNATTENDED"),
    HOLD("HOLD"),
    FAILED("FAILEd"),
}

package com.shrmrm.ft

import app.cash.turbine.test
import com.shrmrm.ft.data.domain.Task
import com.shrmrm.ft.data.domain.TaskLog
import com.shrmrm.ft.data.domain.TaskState
import com.shrmrm.ft.data.repository.FtRepository
import com.shrmrm.ft.data.viewmodels.FtIntent
import com.shrmrm.ft.data.viewmodels.FtViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class FtViewModelTest {
    private val repo = mockk<FtRepository>(relaxed = true)
    private lateinit var viewModel: FtViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { repo.loadAllTasks() } returns flowOf(emptyList())
        every { repo.loadAllExpenses() } returns flowOf(emptyList())
        every { repo.loadAllTaskLogs() } returns flowOf(emptyList())
        viewModel = FtViewModel(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `LoadAll updates UI state with tasks from repository`() =
        runTest {
            val tasks = listOf(Task(id = 1, name = "Test task", created = Instant.now()))
            every { repo.loadAllTasks() } returns flowOf(tasks)

            viewModel.handleIntent(FtIntent.LoadAll)
            viewModel.ftUiViewState.test {
                advanceUntilIdle()
                val state = expectMostRecentItem()
                assertEquals(1, state.tasks.size)
                assertEquals("Test task", state.tasks[0].name)
            }
        }

    @Test
    fun `CreateTask intent calls repository`() =
        runTest {
            val taskName = "New Task"
            coEvery { repo.createTask(taskName) } returns Unit

            viewModel.handleIntent(FtIntent.CreateTask(taskName))
            advanceUntilIdle()

            coVerify { repo.createTask(taskName) }
        }

    @Test
    fun `DeleteTask intent calls repository`() =
        runTest {
            val taskId = 1
            every { repo.deleteTask(taskId) } returns Unit

            viewModel.handleIntent(FtIntent.DeleteTask(taskId))
            advanceUntilIdle()

            verify { repo.deleteTask(taskId) }
        }

    @Test
    fun `UpdateTask intent calls repository`() =
        runTest {
            val taskId = 1
            val newName = "Updated Task"
            every { repo.updateTask(taskId, newName) } returns Unit

            viewModel.handleIntent(FtIntent.UpdateTask(taskId, newName))
            advanceUntilIdle()

            verify { repo.updateTask(taskId, newName) }
        }

    @Test
    fun `CreateExpense intent calls repository`() =
        runTest {
            val name = "Coffee"
            val amount = -500
            coEvery { repo.createExpense(name, amount) } returns Unit

            viewModel.handleIntent(FtIntent.CreateExpense(name, amount))
            advanceUntilIdle()

            coVerify { repo.createExpense(name, amount) }
        }

    @Test
    fun `DeleteExpense intent calls repository`() =
        runTest {
            val expenseId = 101
            every { repo.deleteExpense(expenseId) } returns Unit

            viewModel.handleIntent(FtIntent.DeleteExpense(expenseId))
            advanceUntilIdle()

            verify { repo.deleteExpense(expenseId) }
        }

    @Test
    fun `CompleteTask intent with valid status calls repository`() =
        runTest {
            val taskLog = TaskLog(id = 1, status = TaskState.DONE.status, logDate = Instant.now())
            coEvery { repo.completeTask(taskLog) } returns Unit

            viewModel.handleIntent(FtIntent.CompleteTask(taskLog))
            advanceUntilIdle()

            coVerify { repo.completeTask(taskLog) }
        }

    @Test
    fun `CompleteTask intent with invalid status does NOT call repository`() =
        runTest {
            val taskLog = TaskLog(id = 1, status = "INVALID_STATUS", logDate = Instant.now())

            viewModel.handleIntent(FtIntent.CompleteTask(taskLog))
            advanceUntilIdle()

            coVerify(exactly = 0) { repo.completeTask(any()) }
        }
}

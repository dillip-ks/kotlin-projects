package taskmanagertest

import taskmanager.TaskManager
import taskmanager.taskclass.EducationalTask
import taskmanager.taskclass.PersonalTask
import taskmanager.taskclass.Priority
import taskmanager.taskclass.Status
import taskmanager.taskclass.WorkTask
import taskmanager.taskexceptions.DuplicateTaskException
import taskmanager.taskexceptions.EmptyTitleException
import taskmanager.taskexceptions.InvalidContactNumberException
import taskmanager.taskexceptions.InvalidDateException
import taskmanager.taskexceptions.InvalidPriorityException
import taskmanager.taskexceptions.InvalidStatusException
import taskmanager.taskexceptions.InvalidTaskTypeExeception
import taskmanager.taskexceptions.TaskNotFoundException
import taskmanager.taskexceptions.WrongInputException
import java.time.LocalDate
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TaskManagerTest {
    class EnumTest {
        private val manager = TaskManager()

        @Test
        fun `valid priority enum retuns an enum`() {
            assertEquals(Priority.HIGH, manager.isPriorityValid("HIGH"))
        }

        @Test
        fun `invalid priority enum throws exception`() {
            assertFailsWith<InvalidPriorityException> {
                manager.isPriorityValid("important")
            }
        }

        @Test
        fun `valid status enum returns an enum`() {
            assertEquals(Status.COMPLETED, manager.isStatusValid("COMPLETED"))
        }

        @Test
        fun `invalid status enum throws exception`() {
            assertFailsWith<InvalidStatusException> {
                manager.isStatusValid("important")
            }
        }
    }

    class LocalDateTest {
        private val manager = TaskManager()

        @Test
        fun `valid date string returns LocalDate`() {
            val date = manager.getDate("2026-01-25")

            assertEquals(LocalDate.of(2026, 1, 25), date)
        }

        @Test
        fun `invalid date string throws exception`() {
            assertFailsWith<WrongInputException> {
                manager.getDate("25-01-2026")
            }
        }
    }

    class AddDuplicateDeleteTaskTest {
        private val manager = TaskManager()

        @Test
        fun `add task successfully`() {
            val task =
                PersonalTask(
                    taskTitle = "hello",
                    dueDate = LocalDate.now().plusDays(10),
                    priority = 2,
                )
            manager.addTasks(task)
            assertEquals(1, manager.getAllTasks().size)
        }

        @Test
        fun `duplicate task title throws exception`() {
            val task =
                WorkTask(
                    taskTitle = "hello",
                    deadline = LocalDate.now().plusDays(29),
                    priority = 1,
                )
            manager.addTasks(task)
            assertFailsWith<DuplicateTaskException> {
                manager.addTasks(task)
            }
        }

        @Test
        fun `delete task successfully`() {
            manager.deleteTask("Meeting")
            assertEquals(0, manager.getAllTasks().size)
        }
    }

    class UpdateTaskTest {
        private val manager = TaskManager()

        @Test
        fun `add task successfully`() {
            val task =
                PersonalTask(
                    taskTitle = "hello",
                    dueDate = LocalDate.now().plusDays(10),
                    priority = 2,
                )
            manager.addTasks(task)
            assertEquals(1, manager.getAllTasks().size)
        }

        @Test
        fun `update task with incorrect task title throws exception`() {
            assertFailsWith<TaskNotFoundException> {
                manager.updateTask("hola")
            }
        }
    }

    class ExceptionTests {
        private val manager = TaskManager()

        @Test
        fun `get tasks by type with incorrect type throws exception`() {
            assertFailsWith<InvalidTaskTypeExeception> {
                val data = manager.getTasksByType("Social")
            }
        }
    }

    class PersonalTaskTest {
        private val manager = TaskManager()

        @Test
        fun `no title throws exception`() {
            assertFailsWith<EmptyTitleException> {
                val task =
                    PersonalTask(
                        taskTitle = "",
                        dueDate = LocalDate.now().plusDays(10),
                        priority = 2,
                    )
            }
        }

        @Test
        fun `past due date throws exception`() {
            assertFailsWith<InvalidDateException> {
                val task =
                    PersonalTask(
                        taskTitle = "hola",
                        dueDate = LocalDate.now().minusDays(2),
                        priority = 2,
                    )
            }
        }

        @Test
        fun `past reminder date throws exception`() {
            assertFailsWith<InvalidDateException> {
                val task =
                    PersonalTask(
                        taskTitle = "hola",
                        dueDate = LocalDate.now().plusDays(2),
                        reminderTime = LocalDate.now().minusDays(2),
                        priority = 2,
                    )
            }
        }

        @Test
        fun `wrong priority value throws exception`() {
            assertFailsWith<InvalidPriorityException> {
                val task =
                    PersonalTask(
                        taskTitle = "hola",
                        dueDate = LocalDate.now().plusDays(2),
                        priority = 22,
                    )
            }
        }

        @Test
        fun `wrong contact number throws exception`() {
            assertFailsWith<InvalidContactNumberException> {
                val task =
                    PersonalTask(
                        taskTitle = "hola",
                        dueDate = LocalDate.now().plusDays(2),
                        priority = 2,
                        contact = "987657",
                    )
            }
        }
    }

    class WorkTaskTest {
        private val manager = TaskManager()

        @Test
        fun `no title throws exception`() {
            assertFailsWith<EmptyTitleException> {
                val task =
                    WorkTask(
                        taskTitle = "",
                        deadline = LocalDate.now().plusDays(10),
                        priority = 2,
                    )
            }
        }

        @Test
        fun `past deadline throws exception`() {
            assertFailsWith<InvalidDateException> {
                val task =
                    WorkTask(
                        taskTitle = "hola",
                        deadline = LocalDate.now().minusDays(2),
                        priority = 2,
                    )
            }
        }

        @Test
        fun `wrong priority value throws exception`() {
            assertFailsWith<InvalidPriorityException> {
                val task =
                    WorkTask(
                        taskTitle = "hola",
                        deadline = LocalDate.now().plusDays(2),
                        priority = 22,
                    )
            }
        }
    }

    class EducationalTaskTest {
        private val manager = TaskManager()

        @Test
        fun `no title throws exception`() {
            assertFailsWith<EmptyTitleException> {
                val task =
                    EducationalTask(
                        taskTitle = "",
                        dueDate = LocalDate.now().plusDays(10),
                        priority = 2,
                    )
            }
        }

        @Test
        fun `past deadline throws exception`() {
            assertFailsWith<InvalidDateException> {
                val task =
                    EducationalTask(
                        taskTitle = "hola",
                        dueDate = LocalDate.now().minusDays(2),
                        priority = 2,
                    )
            }
        }

        @Test
        fun `wrong priority value throws exception`() {
            assertFailsWith<InvalidPriorityException> {
                val task =
                    EducationalTask(
                        taskTitle = "hola",
                        dueDate = LocalDate.now().plusDays(2),
                        priority = 22,
                    )
            }
        }
    }
}

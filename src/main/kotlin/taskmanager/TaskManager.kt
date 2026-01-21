package taskmanager

import jdk.jshell.Snippet
import taskmanager.taskclass.EducationalTask
import taskmanager.taskclass.PersonalTask
import taskmanager.taskclass.Priority
import taskmanager.taskclass.Status
import taskmanager.taskclass.Task
import taskmanager.taskclass.WorkTask
import taskmanager.taskexceptions.DuplicateTaskException
import taskmanager.taskexceptions.InvalidPriorityException
import taskmanager.taskexceptions.InvalidStatusException
import taskmanager.taskexceptions.InvalidTaskTypeExeception
import taskmanager.taskexceptions.TaskException
import taskmanager.taskexceptions.TaskNotFoundException
import taskmanager.taskexceptions.WrongInputException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.system.exitProcess

/* This class defines the main logic  of operations that are performed on Tasks() data.
 */
class TaskManager {
    private val tasks = mutableListOf<Task>()

    fun isPriorityValid(priority: String): Priority? = Priority.entries.find { it.name == priority }

    fun isStatusValid(status: String): Status? = Status.entries.find { it.name == status }

    fun operation() {
        print("\nTask Operations:\n1. Add Task\n2. Modify Task\n3. Display Tasks\n4. Display Tasks by Type\n5. Delete Task\n6. None\n")
        print("\nEnter Operation: \t")
        var option = readln().toInt()
        when (option) {
            1 -> {
                val task: Task = createTask()
                addTasks(task)
            }

            2 -> {
                print("\nEnter Task To Update: \t")
                updateTask(readln())
            }

            3 -> {
                printTasks(getAllTasks())
            }

            4 -> {
                print("\nEnter Type Of Task To Display (Personal, Work, Educational): \t")
                printTasks(getTasksByType(readln()))
            }

            5 -> {
                deleteTask(readln())
            }

            6 -> {
                exitProcess(0)
            }

            else -> {
                throw WrongInputException()
            }
        }
    }

    fun getDate(dateString: String): LocalDate {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var date: LocalDate?
        try {
            date = LocalDate.parse(dateString, formatter)
        } catch (e: DateTimeParseException) {
            throw WrongInputException()
        }
        return date
    }

    fun createTask(): Task {
        println("\n Enter the Details of the Task:")
        print("Task Title:\t")
        val title = readln()
        print("Task Priority: (HIGH, MED, LOW)\t")
        val priority = isPriorityValid(readln().uppercase()) ?: throw InvalidPriorityException()

        print("Task Due Date:(yyyy-MM-dd) \t")

        val dueDate: LocalDate = getDate(readln())
        print("Task Type: (Personal, Work, Educational) \t")
        val type = readln()

        when (type) {
            "Personal" -> return PersonalTask(
                taskTitle = title,
                dueDate = dueDate,
                priority = priority,
            )

            "Work" -> return WorkTask(
                taskTitle = title,
                deadline = dueDate,
                priority = priority,
            )

            "Educational" -> return EducationalTask(
                taskTitle = title,
                dueDate = dueDate,
                priority = priority,
            )

            else -> throw InvalidTaskTypeExeception()
        }
    }

    fun updateTask(title: String) {
        var task: Task? = tasks.find { it.taskTitle == title }
        if (task == null) throw TaskNotFoundException()
        when (task) {
            is PersonalTask -> {
                printTask(task)
                println("Enter new details---")

                print("Due Date: \t")
                task.dueDate = getDate(readln())

                print("priority:(HIGH, MED, LOW) \t")
                task.priority = isPriorityValid(readln().uppercase()) ?: throw InvalidPriorityException()

                print("Location: \t")
                task.location = readln()

                print("reminderTime: \t")
                task.reminderTime = getDate(readln())

                print("notes: \t")
                task.notes = readln()

                print("contact: \t")
                task.contact = readln()
            }

            is WorkTask -> {
                printTask(task)
                println("Enter new details---")

                print("deadline: \t")
                task.deadline = getDate(readln())

                print("Project Name: \t")
                task.projectName = readln()

                print("priority: (HIGH, MED, LOW)\t")
                task.priority = isPriorityValid(readln().uppercase()) ?: throw InvalidPriorityException()

                print("assignedTo: \t")
                task.assignedTo = readln()

                print("clientName: \t")
                task.clientName = readln()

                print("status: \t")
                task.status = isStatusValid(readln().uppercase()) ?: throw InvalidStatusException()

                print("estimatedHours: \t")
                task.estimatedHours = readln().toInt()
            }

            is EducationalTask -> {
                printTask(task)

                println("Enter new details---")

                print("dueDate: \t")
                task.dueDate = getDate(readln())

                print("subjectName: \t")
                task.subjectName = readln()

                print("priority:(HIGH, MED, LOW) \t")
                task.priority = isPriorityValid(readln().uppercase()) ?: throw InvalidPriorityException()

                print("instructor: \t")
                task.instructor = readln()

                print("notes: \t")
                task.notes = readln()

                print("classLocation: \t")
                task.classLocation = readln()
            }
        }
    }

    fun addTasks(task: Task) {
        if (tasks.any { it.taskTitle == task.taskTitle }) {
            throw DuplicateTaskException()
        }
        tasks.add(task)
    }

    fun deleteTask(title: String) {
        if (tasks.any { it.taskTitle != title }) {
            throw TaskNotFoundException()
        }
        tasks.removeIf { it.taskTitle == title }
        println("The task was deleted Sucessfully!")
    }

    fun printTask(task: Task) {
        when (task) {
            is PersonalTask -> {
                println("Title: ${task.taskTitle}")
                println("Due Date: ${task.dueDate}")
                println("priority: ${task.priority}")
                println("Location: ${task.location}")
                println("ReminderTime: ${task.reminderTime}")
                println("Notes: ${task.notes}")
                println("Contact: ${task.contact}")
            }

            is WorkTask -> {
                println("Title: ${task.taskTitle}")
                println("projectName: ${task.projectName}")
                println("deadline: ${task.deadline}")
                println("priority: ${task.priority}")
                println("assignedTo: ${task.assignedTo}")
                println("estimatedHours: ${task.estimatedHours}")
                println("clientName: ${task.clientName}")
                println("status: ${task.status}")
            }

            is EducationalTask -> {
                println("Title: ${task.taskTitle}")
                println("subjectName: ${task.subjectName}")
                println("dueDate: ${task.dueDate}")
                println("priority: ${task.priority}")
                println("instructor: ${task.instructor}")
                println("notes: ${task.notes}")
                println("classLocation: ${task.classLocation}")
            }
        }
    }

    fun printTasks(tasks: List<Task>) {
        if (tasks.size == 0) {
            println("There are currently no tasks in task manager.")
            return
        }
        for (task in tasks) {
            when (task) {
                is PersonalTask -> {
                    println("______________________________")
                    println("Title: ${task.taskTitle}")
                    println("Due Date: ${task.dueDate}")
                    println("priority: ${task.priority}")
                    println("Location: ${task.location}")
                    println("ReminderTime: ${task.reminderTime}")
                    println("Notes: ${task.notes}")
                    println("Contact: ${task.contact}")
                }

                is WorkTask -> {
                    println("______________________________")
                    println("Title: ${task.taskTitle}")
                    println("projectName: ${task.projectName}")
                    println("deadline: ${task.deadline}")
                    println("priority: ${task.priority}")
                    println("assignedTo: ${task.assignedTo}")
                    println("estimatedHours: ${task.estimatedHours}")
                    println("clientName: ${task.clientName}")
                    println("status: ${task.status}")
                }

                is EducationalTask -> {
                    println("______________________________")
                    println("Title: ${task.taskTitle}")
                    println("subjectName: ${task.subjectName}")
                    println("dueDate: ${task.dueDate}")
                    println("priority: ${task.priority}")
                    println("instructor: ${task.instructor}")
                    println("notes: ${task.notes}")
                    println("classLocation: ${task.classLocation}")
                }
            }
        }
    }

    fun getTasksByType(type: String): List<Task> =
        when (type) {
            "Personal" -> tasks.filterIsInstance<PersonalTask>()
            "Work" -> tasks.filterIsInstance<WorkTask>()
            "Educational" -> tasks.filterIsInstance<EducationalTask>()
            else -> throw InvalidTaskTypeExeception()
        }

    fun getAllTasks(): List<Task> = tasks
}

fun main() {
    println("(__TASK_MANAGER__)")
    var obj = TaskManager()
    while (true) {
        try {
            obj.operation()
        } catch (e: TaskException) {
            println(e.message)
        } finally {
            continue
        }
    }
}

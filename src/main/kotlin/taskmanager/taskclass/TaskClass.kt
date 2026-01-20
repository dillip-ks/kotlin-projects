package taskmanager.taskclass

import taskmanager.taskexceptions.EmptyTitleException
import taskmanager.taskexceptions.InvalidContactNumberException
import taskmanager.taskexceptions.InvalidDateException
import taskmanager.taskexceptions.InvalidPriorityException
import java.time.LocalDate

/** This data class implements the sealed class Task() to implement Personal Tasks .
 * It has properties related to Personal tasks that are exception handled and tested.
 */
data class PersonalTask(
    override val taskTitle: String,
    var dueDate: LocalDate,
    var location: String? = null,
    var notes: String? = null,
    var priority: Int,
    var reminderTime: LocalDate? = null,
    var contact: String? = null,
) : Task() {
    init {
        if (taskTitle.isBlank()) throw EmptyTitleException()
        if (dueDate.isBefore(LocalDate.now())) throw InvalidDateException()
        if (reminderTime != null && reminderTime!!.isBefore(LocalDate.now())) throw InvalidDateException()
        if (priority !in 1..5) throw InvalidPriorityException()
        if (contact != null && contact!!.length < 10) throw InvalidContactNumberException()
    }
}

/** This data class implements the sealed class Task() to implement Work Tasks .
 * It has properties related to Work tasks that are exception handled and tested.
 */
data class WorkTask(
    override val taskTitle: String,
    var projectName: String? = null,
    var deadline: LocalDate,
    var assignedTo: String? = null,
    var priority: Int,
    var status: String? = null,
    var clientName: String? = null,
    var estimatedHours: Int? = 0,
) : Task() {
    init {
        if (taskTitle.isBlank()) throw EmptyTitleException()
        if (deadline.isBefore(LocalDate.now())) throw InvalidDateException()
        if (priority !in 1..5) throw InvalidPriorityException()
    }
}

/** This data class implements the sealed class Task() to implement Educational Tasks .
 * It has properties related to Educational tasks that are exception handled and tested.
 */
data class EducationalTask(
    override val taskTitle: String,
    var subjectName: String? = null,
    var dueDate: LocalDate,
    var instructor: String? = null,
    var priority: Int,
    var notes: String? = null,
    var classLocation: String? = null,
//    var groupMembers: ArrayList<String>
) : Task() {
    init {
        if (taskTitle.isBlank()) throw EmptyTitleException()
        if (dueDate.isBefore(LocalDate.now())) throw InvalidDateException()
        if (priority !in 1..5) throw InvalidPriorityException()
    }
}

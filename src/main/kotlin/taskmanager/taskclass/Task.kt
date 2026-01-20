package taskmanager.taskclass

/** This class is a sealed class  that is inherited by its subclasses to create different Types of tasks.
 */
sealed class Task {
    abstract val taskTitle: String
}

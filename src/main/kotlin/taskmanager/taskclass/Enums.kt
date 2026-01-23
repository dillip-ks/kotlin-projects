package taskmanager.taskclass

/**
 * Represents the different values the  Priority of a Task can have.
 *
 * This enum is used to assign priority to different tasks (Personal, Work and Educational)
 */
enum class Priority {
    HIGH,
    MEDIUM,
    LOW,
}

/**
 * Represents the different values the  Status of a Task can have.
 *
 * This enum is used to assign status to different tasks (Personal, Work and Educational)
 */

enum class Status {
    COMPLETED,
    PENDING,
    NONE,
}

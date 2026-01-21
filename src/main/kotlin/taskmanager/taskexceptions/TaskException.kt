package taskmanager.taskexceptions

/** This sealed class is implemented by subclasses to handle exceptions during runtime.
 */
sealed class TaskException(
    message: String,
) : IllegalArgumentException(message)

/** The following  classes  handle exceptions during runtime and implement sealed class TaskException.
 */
class EmptyTitleException : TaskException("Task title must not be empty")

class InvalidTaskTypeException : TaskException("The type of Task is invalid")

class DuplicateTaskException : TaskException("The Task is already present")

class TaskNotFoundException : TaskException(" The Task is not available")

class WrongInputException : TaskException("The input is invalid")

class InvalidDateException : TaskException("Due date must not be in the past")

class InvalidPriorityException : TaskException("Priority must be HIGH, MED or LOW")

class InvalidStatusException : TaskException("Status must be PENDING, COMPLETED or NONE")

class InvalidContactNumberException : TaskException("Contact number must be of 10 digits!!")

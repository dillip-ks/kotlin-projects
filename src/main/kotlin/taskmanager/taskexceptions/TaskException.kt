package taskmanager.taskexceptions

sealed class TaskException(
    message: String,
) : IllegalArgumentException(message)

class EmptyTitleException : TaskException("Task title must not be empty")

class InvalidTaskTypeExeception : TaskException("The type of Task is invalid")

class DuplicateTaskException : TaskException("The Task is already present")

class TaskNotFoundException : TaskException(" The Task is not available")

class WrongInputException : TaskException("The input is invalid")

class InvalidDateException : TaskException("Due date must not be in the past")

class InvalidPriorityException : TaskException("Priority must be between 1 and 5")

class InvalidContactNumberException : TaskException("Contact number must be of 10 digits!!")

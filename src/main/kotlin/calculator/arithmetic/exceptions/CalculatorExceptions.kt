package calculator.arithmetic.exceptions

/** The sealed class CalculatorExceptions handles all runtime exceptions that may happen.
 *  It implements RuntimeException which is a type alias of java.lang.RuntimeException.
 *  Division by zero, Invalid token, Not enough operators and Invalid postfix expression are handled in it.
 */
sealed class CalculatorExceptions(
    message: String,
) : RuntimeException(message) {
    class DivideByZeroException : CalculatorExceptions("Division by Zero is not allowed")

    class InvalidTokenExpression(
        token: String,
    ) : CalculatorExceptions("Invalid token")

    class NotEnoughOperandsException(
        operator: String,
    ) : CalculatorExceptions("Not enough operands for operator")

    class InvalidExpressionException : CalculatorExceptions("Invalid postfix expression")
}

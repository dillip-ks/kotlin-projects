import arithmetic.exceptions.CalculatorExceptions
import arithmetic.operations.Operations
import kotlin.math.abs

const val EPSILON = 1e-6

/** This Object  implements the Operations interface with type Int.
 */
object IntegerOperations : Operations<Int> {
    override fun parse(token: String): Int = token.toIntOrNull() ?: throw CalculatorExceptions.InvalidTokenExpression(token)

    override fun add(
        a: Int,
        b: Int,
    ): Int = a + b

    override fun subtract(
        a: Int,
        b: Int,
    ): Int = a - b

    override fun multiply(
        a: Int,
        b: Int,
    ): Int = a * b

    override fun divide(
        a: Int,
        b: Int,
    ): Int {
        if (b == 0) throw CalculatorExceptions.DivideByZeroException()
        return a / b
    }
}

/** This Object  implements the Operations interface with type Float.
 */
object FloatOperations : Operations<Float> {
    override fun parse(token: String): Float = token.toFloatOrNull() ?: throw CalculatorExceptions.InvalidTokenExpression(token)

    override fun add(
        a: Float,
        b: Float,
    ): Float = a + b

    override fun subtract(
        a: Float,
        b: Float,
    ): Float = a - b

    override fun multiply(
        a: Float,
        b: Float,
    ): Float = a * b

    override fun divide(
        a: Float,
        b: Float,
    ): Float {
        if (abs(b) < EPSILON) throw CalculatorExceptions.DivideByZeroException()
        return a / b
    }
}

/** This is the Class file where expression evaluation logic is handled.
 *  The evaluate function uses ArrayDeque<T> as a Stack to keep track of Int and Float Operands.
 */
class Calculator<T : Number>(
    private val operators: Operations<T>,
) {
    fun evaluate(expression: String): T {
        val stack = ArrayDeque<T>()
        val tokens = expression.trim().split("\\s+".toRegex())

        for (token in tokens) {
            when (token) {
                "+", "-", "*", "/" -> {
                    if (stack.size < 2) {
                        throw CalculatorExceptions.NotEnoughOperandsException(token)
                    }
                    val b = stack.removeLast()
                    val a = stack.removeLast()

                    val result =
                        when (token) {
                            "+" -> operators.add(a, b)
                            "-" -> operators.subtract(a, b)
                            "*" -> operators.multiply(a, b)
                            "/" -> operators.divide(a, b)
                            else -> throw CalculatorExceptions.InvalidExpressionException()
                        }
                    stack.addLast(result as T)
                }

                else -> {
                    stack.addLast(operators.parse(token))
                }
            }
        }
        if (stack.size > 1) throw CalculatorExceptions.InvalidExpressionException()
        return stack.last()
    }
}

/** This is the point where the execution of the program starts.
 *  It also provides the interface to take input and give output.
 */
fun main() {
    println("--|____WELCOME TO COMMAND LINE CALCULATOR_____|--")
    val objInt = Calculator(IntegerOperations)
    val objFloat = Calculator(FloatOperations)
    while (true) {
        print("Choose the type of operations you wish to perform (1.Int \t 2.Double \t  3.EXIT): \t")
        when (readln().toIntOrNull()) {
            1 -> {
                try {
                    print("Input (postfix): \t")
                    val result = objInt.evaluate(readln())
                    println("output: $result")
                } catch (e: CalculatorExceptions) {
                    println(e.message)
                    continue
                }
            }

            2 -> {
                print("Input (postfix):\t")
                val result = objFloat.evaluate(readln())
                println("Output: $result")
            }

            3 -> {
                println("THANK YOU")
                break
            }

            else -> {
                println("Please choose the type of operation you wish to do.Try Again!!")
                continue
            }
        }
    }
}

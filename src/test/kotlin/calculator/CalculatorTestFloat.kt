package calculator

import calculator.arithmetic.exceptions.CalculatorExceptions
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

const val DELTA: Float = 0.01F

class CalculatorTestFloat {
    val objFloat = Calculator(FloatOperations)

    @Test
    fun `should throw a DivideByZeroException when division by zero`() {
        val exception =
            assertFailsWith<CalculatorExceptions> {
                objFloat.evaluate("5.65 0.0 /")
            }

        assertEquals("Division by Zero is not allowed", exception.message)
    }

    @Test
    fun `should throw a InvalidExpressionException when expression is invalid`() {
        val exception =
            assertFailsWith<CalculatorExceptions> {
                objFloat.evaluate("5.9 0 6.5 7.112")
            }

        assertEquals("Invalid postfix expression", exception.message)
    }

    @Test
    fun `should throw a NotEnoughOperandsException when there are not enough operands for the operation`() {
        val exception =
            assertFailsWith<CalculatorExceptions> {
                objFloat.evaluate("5.54 0.32 + -")
            }

        assertEquals("Not enough operands for operator", exception.message)
    }

    @Test
    fun `should throw a InvalidTokenExpression when the token is not recognised by the system`() {
        val exception =
            assertFailsWith<CalculatorExceptions> {
                objFloat.evaluate("5 0.4342 %")
            }

        assertEquals("Invalid token", exception.message)
    }

    @Test
    fun `addition of two positive Float`() {
        assertEquals((6.024).toFloat(), objFloat.evaluate("2.9 3.124 +"), DELTA)
    }

    @Test
    fun `substraction of two positive Float`() {
        assertEquals((-0.1).toFloat(), objFloat.evaluate("2.9 3 -"), DELTA)
    }

    @Test
    fun `product of two positive Float`() {
        assertEquals((6.3).toFloat(), objFloat.evaluate("2.1 3 *"), DELTA)
    }

    @Test
    fun `division of two positive Float`() {
        assertEquals(4.0.toFloat(), objFloat.evaluate("10 2.5 /"), DELTA)
    }

    @Test
    fun `addition of two negative Float`() {
        assertEquals((-9.33).toFloat(), objFloat.evaluate("-2.33 -7.0 +"), DELTA)
    }

    @Test
    fun `substraction of two negative Float`() {
        assertEquals((4.67).toFloat(), objFloat.evaluate("-2.33 -7.0 -"), DELTA)
    }

    @Test
    fun `product of two negative Float`() {
        assertEquals(6.4.toFloat(), objFloat.evaluate("-2.0 -3.2 *"), DELTA)
    }

    @Test
    fun `division of two negative Float`() {
        assertEquals(5.0.toFloat(), objFloat.evaluate("-10.0 -2 /"), DELTA)
    }

    @Test
    fun `addition of two opposite signed Float`() {
        assertEquals((4.67).toFloat(), objFloat.evaluate("-2.33 7.0 +"), DELTA)
    }

    @Test
    fun `substraction of two opposite signed Float`() {
        assertEquals(17.7101.toFloat(), objFloat.evaluate("5.6 -12.1101 -"), DELTA)
    }

    @Test
    fun `product of two opposite signed Float`() {
        assertEquals((-8.1558).toFloat(), objFloat.evaluate("-2.3 3.546 *"), DELTA)
    }

    @Test
    fun `division of two opposite signed Float`() {
        assertEquals((-5.67).toFloat(), objFloat.evaluate("11.34 -2 /"), DELTA)
    }
}

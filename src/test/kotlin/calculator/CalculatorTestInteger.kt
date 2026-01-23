package calculator

import calculator.arithmetic.exceptions.CalculatorExceptions
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CalculatorTestInteger {
    val objInt = Calculator(IntegerOperations)

    @Test
    fun `addition of two positive integers`() {
        assert(5 == objInt.evaluate("2 3 +"))
    }

    @Test
    fun `substraction of two positive integers`() {
        assert(-1 == objInt.evaluate("2 3 -"))
    }

    @Test
    fun `product of two positive integers`() {
        assert(6 == objInt.evaluate("2 3 *"))
    }

    @Test
    fun `division of two positive integers`() {
        assert(5 == objInt.evaluate("10 2 /"))
    }

    @Test
    fun `addition of two negative integers`() {
        assert(-9 == objInt.evaluate("-2 -7 +"))
    }

    @Test
    fun `substraction of two negative integers`() {
        assert(-3 == objInt.evaluate("-5 -2 -"))
    }

    @Test
    fun `product of two negative integers`() {
        assert(6 == objInt.evaluate("-2 -3 *"))
    }

    @Test
    fun `division of two negative integers`() {
        assert(5 == objInt.evaluate("-10 -2 /"))
    }

    @Test
    fun `addition of two opposite signed integers`() {
        assert(-2 == objInt.evaluate("5 -7 +"))
    }

    @Test
    fun `substraction of two opposite signed integers`() {
        assert(-17 == objInt.evaluate("-5 12 -"))
    }

    @Test
    fun `product of two opposite signed integers`() {
        assert(-6 == objInt.evaluate("-2 3 *"))
    }

    @Test
    fun `division of two opposite signed integers`() {
        assert(-5 == objInt.evaluate("10 -2 /"))
    }

    @Test
    fun `should throw a DivideByZeroException when division by zero`() {
        val exception =
            assertFailsWith<CalculatorExceptions> {
                objInt.evaluate("5 0 /")
            }

        assertEquals("Division by Zero is not allowed", exception.message)
    }

    @Test
    fun `should throw a InvalidExpressionException when expression is invalid`() {
        val exception =
            assertFailsWith<CalculatorExceptions> {
                objInt.evaluate("5 0 6 7")
            }

        assertEquals("Invalid postfix expression", exception.message)
    }

    @Test
    fun `should throw a NotEnoughOperandsException when there are not enough operands for the operation`() {
        val exception =
            assertFailsWith<CalculatorExceptions> {
                objInt.evaluate("5 2 + -")
            }

        assertEquals("Not enough operands for operator", exception.message)
    }

    @Test
    fun `should throw a InvalidTokenExpression when the token is not recognised by the system`() {
        val exception =
            assertFailsWith<CalculatorExceptions> {
                objInt.evaluate("5 0 %")
            }

        assertEquals("Invalid token", exception.message)
    }
}

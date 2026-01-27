package currencyexchangetest

import currencyexchange.isValidCurrency
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CurrencyExchangeTest {
    class ValidCurrencyTest {
        @Test
        fun `valid currency test returns a Boolean true`() {
            assertTrue { isValidCurrency("INR") }
        }

        @Test
        fun `invalid currency test returns a Boolean false`() {
            assertFalse { isValidCurrency("AAA") }
        }
    }
}

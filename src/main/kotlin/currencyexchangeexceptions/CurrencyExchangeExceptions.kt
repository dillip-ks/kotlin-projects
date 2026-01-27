package currencyexchangeexceptions

sealed class CurrencyExchangeExceptions(
    message: String?,
) : RuntimeException(message)

class InvalidCurrencyException : CurrencyExchangeExceptions("The entered currency is Invalid.")

class InvalidAmountException : CurrencyExchangeExceptions("The entered amount is Invalid ")

class DataFetchError(
    message: String?,
) : CurrencyExchangeExceptions(message)

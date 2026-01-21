package currencyexchangeexceptions

sealed class CurrencyExchangeExceptions(
    message: String?,
) : RuntimeException(message)

class InvalidCurrencyException : CurrencyExchangeExceptions("The currency is not valid")

class InvalidAmountException : CurrencyExchangeExceptions("The amount entered is invalid")

class DataFetchException(
    message: String?,
) : CurrencyExchangeExceptions(message)

package currencyexchange

import com.google.gson.Gson
import currencyexchangeexceptions.CurrencyExchangeExceptions
import currencyexchangeexceptions.DataFetchError
import currencyexchangeexceptions.InvalidAmountException
import currencyexchangeexceptions.InvalidCurrencyException
import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import jdk.jfr.DataAmount
import java.nio.channels.UnresolvedAddressException
import javax.xml.crypto.Data
import kotlin.system.exitProcess
import kotlin.text.get

data class ApiExchangeRates(
    val data: Map<String, Double>,
)

fun isValidCurrency(currency: String): Boolean =
    try {
        Currency.valueOf(currency)
        true
    } catch (e: IllegalArgumentException) {
        false
    }

suspend fun getExchangeRate(
    sourceCurrency: String,
    destinationCurrency: String,
    amount: Double,
): Double? {
    var rate: Double?
    val gson = Gson()
    val client = HttpClient()
    val apiKey = dotenv()["CURRENCY_EXCHANGE_API_KEY"]
    val url =
        "https://api.freecurrencyapi.com/v1/latest?apikey=$apiKey&base_currency=$sourceCurrency&currencies=$destinationCurrency"

    try {
        val httpResponse: HttpResponse =
            client.get(
                url,
            ) {
                timeout {
                    requestTimeoutMillis = 60000
                }
            }
        if (httpResponse.status.value == 200) {
            val data: String = httpResponse.body<String>().toString()

            rate = gson.fromJson(data, ApiExchangeRates::class.java).data[destinationCurrency]
        } else {
            throw DataFetchError("Unable to fetch the exchange rate")
        }
    } catch (e: UnresolvedAddressException) {
        throw DataFetchError("Network Error")
    } catch (e: IllegalArgumentException) {
        throw DataFetchError("Illegal argument")
    } catch (e: Exception) {
        throw DataFetchError("Unknown Error Occurred. Try Again.")
    }

    return rate
}

suspend fun main() {
    while (true) {
        try {
            println("\nTask Operations:")
            println("1. Get Currency Exchange Information")
            println("2. None")
            print("Input: ")

            when (readln().toInt()) {
                1 -> {
                    print("Enter Source Currency: ")
                    val sourceCurrency = readln().uppercase()
                    if (sourceCurrency.length != 3 || !isValidCurrency(sourceCurrency)) {
                        throw InvalidCurrencyException()
                    }

                    print("Enter Source Amount: ")
                    val sourceAmount =
                        try {
                            readln().toDouble()
                        } catch (e: Exception) {
                            throw InvalidAmountException()
                        }

                    print("Enter Destination Currency: ")
                    val destinationCurrency = readln().uppercase()

                    if (destinationCurrency.length != 3 || !isValidCurrency(destinationCurrency)) {
                        throw InvalidCurrencyException()
                    }

                    val rate = getExchangeRate(sourceCurrency, destinationCurrency, sourceAmount)
                    if (rate != null) {
                        val amount = sourceAmount * rate
                        println("\nExchange Rates: $sourceCurrency 1 = $destinationCurrency $rate")
                        println("Destination Amount: $amount $")
                    } else {
                        println("The currency exchange was not found")
                    }
                }

                2 -> {
                    exitProcess(0)
                }

                else -> {
                    println("Invalid Input. Try Again")
                }
            }
        } catch (e: CurrencyExchangeExceptions) {
            println(e.printStackTrace())
            continue
        }
    }
}

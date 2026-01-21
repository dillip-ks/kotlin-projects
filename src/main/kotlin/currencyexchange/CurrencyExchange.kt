package currencyexchange
import com.google.gson.Gson
import currencyexchangeexceptions.CurrencyExchangeExceptions
import currencyexchangeexceptions.DataFetchException
import currencyexchangeexceptions.InvalidAmountException
import currencyexchangeexceptions.InvalidCurrencyException
import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlin.system.exitProcess

data class ApiExchangeRates(
    val data: Map<String, Double>,
)

suspend fun main() {
    val dotenv =
        dotenv {
            directory = "./"
            filename = ".env"
            ignoreIfMissing = false
        }

    val gson = Gson()
    val client = HttpClient()

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
                    if (sourceCurrency.length != 3) {
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
                    if (destinationCurrency.length != 3) {
                        throw InvalidCurrencyException()
                    }

                    val apiKey = dotenv["CURRENCY_EXCHANGE_API_KEY"]
                    val url =
                        "https://api.freecurrencyapi.com/v1/latest?apikey=$apiKey&base_currency=$sourceCurrency&currencies=$destinationCurrency"

                    val httpResponse: HttpResponse =
                        client.get(
                            url,
                        ) {
                            timeout {
                                requestTimeoutMillis = 6000
                            }
                        }

                    println(httpResponse.status::class.simpleName)
                    if(httpResponse.status != ht)

                    val data: String = httpResponse.body<String>().toString()

                    val map = gson.fromJson(data, ApiExchangeRates::class.java)

                    val rate = map.data[destinationCurrency]

                    if (rate != null) {
                        val amount = sourceAmount * rate
                        println("\nExchange Rates: $sourceCurrency 1 = $destinationCurrency $rate")
                        println("Destination Amount: $amount")
                    } else {
                        println("The currency exchange was not found")
                    }
                }

                2 -> {
                    println("Thank you")
                    exitProcess(0)
                }

                else -> {
                    println("Invalid input")
                }
            }
        } catch (e: Exception) {
            println(e.message)
        } finally {
            continue
        }
    }
}

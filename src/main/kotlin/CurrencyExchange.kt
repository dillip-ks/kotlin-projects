
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import org.json.JSONObject
import kotlin.system.exitProcess

data class ApiExchangeRates(
    val data: Map<String, Double>,
)

suspend fun main() {
    val gson = Gson()
    val client = HttpClient()

    while (true) {
        println("\nTask Operations:")
        println("1. Get Currency Exchange Information")
        println("2. None")
        print("Input: ")

        when (readln().toInt()) {
            1 -> {
                print("Enter Source Currency: ")
                val sourceCurrency = readln().uppercase()

                print("Enter Source Amount: ")
                val sourceAmount = readln().toDouble()

                print("Enter Destination Currency: ")
                val destinationCurrency = readln().uppercase()

                val apiKey = "fca_live_hE1LIzdRV52c3MBVRLRA91ydRj6ZSF3mposN08sy"
                val url =
                    "https://api.freecurrencyapi.com/v1/latest?apikey=$apiKey&base_currency=$sourceCurrency&currencies=$destinationCurrency"

                try {
                    val httpResponse: HttpResponse =
                        client.get(
                            url,
                        )
                    val data: String = httpResponse.body<String>().toString()

                    val map = gson.fromJson(data, ApiExchangeRates::class.java)

                    val rate = map.data[destinationCurrency]

                    if (rate != null) {
                        val amount = sourceAmount * rate
                        println("\nExchange Rates: $sourceCurrency 1 = $destinationCurrency $rate")
                        println("Destination Amount: $amount")
                    } else {
                        println("uicbvrseuvvuyew")
                    }
                } catch (e: Exception) {
                    println("Error fetching exchange rates: ${e.message}")
                }
            }

            2 -> {
                println("THank you")
                exitProcess(0)
            }

            else -> {
                println("Invalid input")
            }
        }
    }
}

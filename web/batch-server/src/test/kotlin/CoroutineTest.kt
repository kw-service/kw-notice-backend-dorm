import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


suspend fun sendMessage(name: String): String {
    delay(1000L)
    println("message : $name")
    return name
}

fun main() {
    runBlocking {

        val deferreds = mutableListOf<Deferred<String>>()
        for (i in 0..10) {
            val deferred = async {
                sendMessage("hello$i")
            }
            deferreds.add(deferred)
        }
        for (deferred in deferreds) deferred.await()
    }
}
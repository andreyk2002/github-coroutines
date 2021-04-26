package samples

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SampleTest {
    @Test
    fun testDelayInSuspend() = runBlockingTest {
        val realStartTime = System.currentTimeMillis()
        val virtualStartTime = currentTime

        foo()

        println("${System.currentTimeMillis() - realStartTime} ms")
        println("${currentTime - virtualStartTime} ms")
    }

    suspend fun foo() {
        delay(1000) // auto-advances without delay
        println("foo")       // executes eagerly when foo() is called
    }

    @Test
    fun testDelayInLaunch() = runBlockingTest {
        val realStartTime = System.currentTimeMillis()
        val virtualStartTime = currentTime

        bar()

        println("${System.currentTimeMillis() - realStartTime} ms")
        println("${currentTime - virtualStartTime} ms")                  }

    suspend fun bar() = coroutineScope {
        launch {
            delay(1000)
            println("bar")
        }
    }
}
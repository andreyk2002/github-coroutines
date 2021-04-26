package tasks

import contributors.MockGithubService
import contributors.concurrentProgressResults
import contributors.testRequestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class ChannelsRequestKtTest {
    @ExperimentalCoroutinesApi
    @Test
    fun testChannels() = runBlockingTest {
        val startTime = currentTime
        var index = 0
        loadContributorsChannels(MockGithubService, testRequestData) {
                _, _ ->
            val expected = concurrentProgressResults[index++]
            val time = currentTime - startTime
            Assert.assertEquals("Expected intermediate result after virtual ${expected.timeFromStart} ms:",
                expected.timeFromStart, time)

        }
    }
}

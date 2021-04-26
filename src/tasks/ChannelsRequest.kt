package tasks

import contributors.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

suspend fun loadContributorsChannels(
    service: GitHubService,
    req: RequestData,
    updateResults: suspend (List<User>, completed: Boolean) -> Unit
) {
    coroutineScope {
        val repos = service
            .getOrgRepos(req.org)
            .also { logRepos(req, it) }
            .body() ?: listOf()
        val channel = Channel<List<User>>()
        val i = AtomicInteger()
        for (repo in repos) {
            launch {
                val users = service.getRepoContributors(req.org, repo.name)
                    .also { logUsers(repo, it) }
                    .bodyList()
                channel.send(users)
            }
        }
        val allUsers = emptyList<User>().toMutableList()
        repeat(repos.size){
            val users = channel.receive()
            allUsers += users
            updateResults(allUsers.aggregate(), it == repos.lastIndex)
        }
    }
}

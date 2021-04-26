package tasks

import contributors.User

fun List<User>.aggregate(): List<User>{
    return this.groupBy { it.login }
        .map { (login, group) -> User(login, group.sumBy { it.contributions }) }
        .sortedByDescending { it.contributions }
}

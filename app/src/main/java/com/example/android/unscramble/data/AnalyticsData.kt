package com.example.android.unscramble.data


data class Analytics(
    val characterCount: Int,
    val occurrences: List<String>,
)

val analyticsData: List<Analytics>
    get() {
        val characterCountWordsMap: Map<Int, List<String>> = allWords.groupBy {
            it.length
        }

        return characterCountWordsMap.map { listEntry ->
            Analytics(
                characterCount = listEntry.key,
                occurrences = listEntry.value
            )
        }.sortedBy { analytic ->
            analytic.characterCount
        }
    }

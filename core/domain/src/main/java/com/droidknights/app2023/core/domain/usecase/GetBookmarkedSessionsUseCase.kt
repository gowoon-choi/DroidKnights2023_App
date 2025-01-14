package com.droidknights.app2023.core.domain.usecase

import com.droidknights.app2023.core.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetBookmarkedSessionsUseCase @Inject constructor(
    private val getSessionsUseCase: GetSessionsUseCase,
    private val getBookmarkedSessionIdsUseCase: GetBookmarkedSessionIdsUseCase
) {

    suspend operator fun invoke(): Flow<List<Session>> {
        return flow {
            emit(getSessionsUseCase())
        }.zip(getBookmarkedSessionIdsUseCase()) { allSession, bookmarkedSessions ->
            allSession
                .filter { session -> bookmarkedSessions.contains(session.id) }
                .sortedBy { it.startTime }
        }
    }
}

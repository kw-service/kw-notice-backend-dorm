package com.knet.dormitory

import com.knet.dormitory.domain.notice.Notice
import com.knet.dormitory.domain.notice.repository.NoticeRepository
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
@ContextConfiguration
class NoticeRepositoryTest {

    @Autowired
    lateinit var noticeRepository: NoticeRepository

    @Test
    @ExperimentalCoroutinesApi
    fun `notice가 converter를 통해서 저장하고 해당 내용을 title을 이용해서 불러오기`() = runTest {
        // given
        val title = "test title"
        val notice = Notice(
            title = title,
            writerId = "yellow",
            writerName = "노랑",
            createdAt = LocalDate.now()
        )
        // when
        noticeRepository.save(notice).awaitSingleOrNull()

        val savedNotice: Notice = (noticeRepository.findByTitle(title = title).awaitSingleOrNull()
            ?: IllegalStateException("조회가 가능해야합니다.")) as Notice

        advanceUntilIdle()
        // then
        assertTrue { savedNotice.info.title == notice.info.title }
        assertTrue { savedNotice.info.createdAt == notice.info.createdAt }
        assertTrue { savedNotice.writer.id == notice.writer.id }
        assertTrue { savedNotice.writer.name == notice.writer.name }
    }
}
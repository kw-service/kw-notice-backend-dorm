package com.knet.batch.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.PlatformTransactionManager
import java.util.*

@EnableScheduling
@Configuration
@EnableBatchProcessing
class DormitorySchedulingConfig(
    private val monitoringTasklet: MonitoringTasklet,
    private val jobLauncher: JobLauncher,
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {
    @Bean
    fun monitoringJob(): Job = job(jobRepository) {
        tasklet(jobRepository, transactionManager, monitoringTasklet)
    }

    @Scheduled(cron = "0/10 * * * * *")
    fun dormScheduling() {
        val param = JobParametersBuilder()
            .addDate("date", Date())
            .toJobParameters()
        jobLauncher.run(monitoringJob(), param)
    }
}

private fun tasklet(
    jobRepository: JobRepository,
    transactionManager: PlatformTransactionManager,
    tasklet: Tasklet
): Step = StepBuilder("dormitory notice monitoring", jobRepository).tasklet(tasklet, transactionManager).build()

private fun job(jobRepository: JobRepository, init: () -> Step): Job =
    JobBuilder("dormitory notice list job", jobRepository)
        .start(init()).build()

inline fun <reified T> getLogger(): Logger = LoggerFactory.getLogger(T::class.java)
package com.yapp.cvs.support

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer

class RunUniqueIdIncrementer : RunIdIncrementer() {
    private val RUN_ID = "run.id"

    override fun getNext(parameters: JobParameters?): JobParameters {
        val params = parameters ?: JobParameters()
        return JobParametersBuilder()
            .addLong(RUN_ID, params.getLong(RUN_ID, 0L) + 1)
            .toJobParameters()
    }
}

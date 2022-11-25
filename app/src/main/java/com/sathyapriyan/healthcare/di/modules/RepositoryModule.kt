package com.sathyapriyan.healthcare.di.modules

import com.sathyapriyan.healthcare.data.remote.HealthCareApi
import com.sathyapriyan.healthcare.data.repository.HealthCareRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHealthCareRepository(
        healthCareApi: HealthCareApi
    ): HealthCareRepository {

        return HealthCareRepository(healthCareApi = healthCareApi)

    }

}
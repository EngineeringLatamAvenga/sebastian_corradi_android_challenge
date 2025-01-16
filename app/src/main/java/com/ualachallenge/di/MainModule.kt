package com.ualachallenge.di

import com.ualachallenge.domain.usecases.FilterCitiesUseCase
import com.ualachallenge.repository.CitiesRepository
import com.ualachallenge.repository.LocalCitiesDataSource
import com.ualachallenge.repository.RemoteCitiesDataSource
import com.ualachallenge.domain.usecases.GetCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun providesLocalDatasoure(): LocalCitiesDataSource = LocalCitiesDataSource()

    @Provides
    fun providesRemoteDataSource(): RemoteCitiesDataSource = RemoteCitiesDataSource()

    @Provides
    fun providesCitiesRepository(localCitiesDataSource: LocalCitiesDataSource,
                                 remoteCitiesDataSource: RemoteCitiesDataSource
    ): CitiesRepository = CitiesRepository(localCitiesDataSource, remoteCitiesDataSource)

    @Provides
    fun provideGetCitiesUseCase(citiesRepository: CitiesRepository): GetCitiesUseCase =
        GetCitiesUseCase(citiesRepository)

    @Provides
    fun provideFilterCitiesUseCase(): FilterCitiesUseCase =
        FilterCitiesUseCase()

}


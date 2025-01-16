package com.ualachallenge.di

import com.ualachallenge.domain.usecases.FilterCitiesUseCase
import com.ualachallenge.repository.CitiesRepository
import com.ualachallenge.repository.datasources.LocalCitiesDataSource
import com.ualachallenge.repository.datasources.RemoteCitiesDataSource
import com.ualachallenge.domain.usecases.GetCitiesUseCase
import com.ualachallenge.domain.usecases.GetFavouritesUseCase
import com.ualachallenge.domain.usecases.HandleFavouriteUseCase
import com.ualachallenge.repository.data.db.dao.CityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun providesLocalDatasoure(cityDao: CityDao): LocalCitiesDataSource = LocalCitiesDataSource(cityDao)

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

    @Provides
    fun provideGetFavoritesUseCase(citiesRepository: CitiesRepository): GetFavouritesUseCase =
        GetFavouritesUseCase(citiesRepository)

    @Provides
    fun provideHandleFavouriteUseCase(citiesRepository: CitiesRepository): HandleFavouriteUseCase=
        HandleFavouriteUseCase(citiesRepository)

}


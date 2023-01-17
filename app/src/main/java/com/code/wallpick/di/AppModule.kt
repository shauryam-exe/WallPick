package com.code.wallpick.di

import com.code.wallpick.data.remote.RetrofitHelper
import com.code.wallpick.data.remote.WallpaperRepository
import com.code.wallpick.data.remote.WallpaperRepositoryImpl
import com.code.wallpick.data.remote.WallpapersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWallpaperService(): WallpapersService {
        return RetrofitHelper.getInstance().create(WallpapersService::class.java)
    }

    @Provides
    @Singleton
    fun provideWallpaperRepository(wallpaperService: WallpapersService): WallpaperRepository {
        return WallpaperRepositoryImpl(wallpaperService)
    }


}
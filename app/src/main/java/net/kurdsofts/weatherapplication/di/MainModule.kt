package net.kurdsofts.weatherapplication.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import net.kurdsofts.weatherapplication.data.retrofit.DataApi
import net.kurdsofts.weatherapplication.repository.MainRepository
import net.kurdsofts.weatherapplication.repository.TimeZoneRepository
import net.kurdsofts.weatherapplication.util.DispatcherProvider
import net.kurdsofts.weatherapplication.util.UtilConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Singleton
    @Provides
    fun provideGlideRequestManager(@ApplicationContext context: Context): RequestManager {
        return Glide.with(context)
    }

    @Singleton
    @Provides
    fun provideTimeZoneApi(): DataApi = Retrofit.Builder()
        .baseUrl(UtilConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(DataApi::class.java)

    @Singleton
    @Provides
    fun provideMainRepository(api: DataApi): MainRepository = TimeZoneRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}
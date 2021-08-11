package com.islam.task.di

import com.islam.githubapp.data.repositories.FakeMainRepositoryUITest
import com.islam.githubapp.data.repositories.MainRepository
import com.islam.githubapp.di.AppModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object AppModuleTest {

    @Singleton
    @Provides
    fun provideFakeMainRepositoryUITestRepoForUI() =
        FakeMainRepositoryUITest() as MainRepository

}


















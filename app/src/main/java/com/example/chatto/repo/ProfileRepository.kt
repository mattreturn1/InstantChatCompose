package com.example.chatto.repo

import com.example.chatto.domain.db.Dao
import com.example.chatto.domain.vo.DbProfile
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


interface ProfileRepository {

    fun getDbProfile(): Flow<List<DbProfile>>

    suspend fun insertDbProfile(dbProfile: DbProfile)

    suspend fun deleteDbProfile(dbProfile: DbProfile)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileModule {
    @Binds
    @Singleton
    abstract fun provideTaskRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val dao: Dao
) : ProfileRepository {

    override fun getDbProfile(): Flow<List<DbProfile>> = dao.getDbProfile()
    override suspend fun deleteDbProfile(dbProfile: DbProfile) = dao.deleteDbProfile(dbProfile)
    override suspend fun insertDbProfile(dbProfile: DbProfile) = dao.insertDbProfile(dbProfile)


}


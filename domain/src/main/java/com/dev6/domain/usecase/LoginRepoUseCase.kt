package com.dev6.domain.usecase

import com.dev6.core.base.UiState
import com.dev6.core.enum.LoginType
import com.dev6.core.exception.JoinException
import com.dev6.core.exception.NotFoundException
import com.dev6.domain.entitiyRepo.LoginEntitiy
import com.dev6.domain.entitiyRepo.UserEntity
import com.dev6.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class LoginRepoUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    fun login(loginEntityRepo: LoginEntitiy): Flow<UiState<UserEntity>> = flow {




        if(loginEntityRepo.loginMethod== LoginType.email) {
            if (loginEntityRepo.email.isNullOrEmpty()) throw Exception()
            if (loginEntityRepo.password.isNullOrEmpty()) throw Exception()
        }

        //todo 확장함수로 정규식 표현 만들기 이메일 형식이 올바르지 않은거 체크


        //시작할때 로딩을 emit
        emit(UiState.Loding)

        val response = loginRepository.login(loginEntityRepo)
        if (response.isSuccessful) {
            //Body가 없을경우, exception
            val userEntityRepo = response.body() ?: throw Exception("사용자 값을 반환하지 않았습니다.")
            emit(UiState.Success(userEntityRepo))
        } else {
            when (response.code()) {
                404 -> {

                    if(loginEntityRepo.loginMethod == LoginType.email) throw  NotFoundException(response.message()) else throw  JoinException(response.message() , loginEntityRepo )

                }
                else -> {

                }


            }

        }
    }.catch { error ->
        emit(UiState.Error(error))
        Timber.d("에러에염 $error")

    }

}
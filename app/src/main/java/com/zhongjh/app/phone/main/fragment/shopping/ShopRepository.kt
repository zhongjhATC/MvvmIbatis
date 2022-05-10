package com.zhongjh.app.phone.main.fragment.shopping

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.ApiResponse
import com.zhongjh.app.data.http.service.BannerApi
import com.zhongjh.app.phone.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * 跟api关联的类
 * @author zhongjh
 * @date 2022/5/7
 */
class ShopRepository @Inject constructor(
    private val bannerApi: BannerApi,
    private val ioDispatcher: CoroutineDispatcher
) : Repository {

    @WorkerThread
    fun getBanners(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ) = flow {
        bannerApi.json()
//            .onError {
//                /** maps the [ApiResponse.Failure.Error] to the [PokemonErrorResponse] using the mapper. */
//                map(ErrorResponseMapper) {
//                    onError("[Code: $code]: $message")
//                }
//            }
//            // handles the case when the API request gets an exception response.
//            // e.g., network connection error.
//            .onException {
//                onError(message)
//            }
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)


}
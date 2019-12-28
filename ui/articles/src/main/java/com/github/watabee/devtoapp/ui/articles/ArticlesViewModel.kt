package com.github.watabee.devtoapp.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.watabee.devtoapp.data.api.DevToApi
import com.github.watabee.devtoapp.util.CoroutineDispatchers
import javax.inject.Inject

internal class ArticlesViewModel @Inject constructor(
    private val devToApi: DevToApi,
    private val dispatchers: CoroutineDispatchers
) : ViewModel() {

    val articles: LiveData<List<ArticleUiModel>> = MutableLiveData<List<ArticleUiModel>>(
        (1..20).map {
            ArticleUiModel(
                id = it,
                title = "title-$it",
                readablePublishDate = "Dec 27",
                userImage = "https://res.cloudinary.com/practicaldev/image/fetch/s--k2S4DpS_--/c_fill,f_auto,fl_progressive,h_90,q_auto,w_90/https://thepracticaldev.s3.amazonaws.com/uploads/user/profile_image/292698/21fb81d5-2f25-423a-ba50-533e4fd5279c.png",
                username = "hogehoge",
                tagList = listOf("android", "ios", "hogehoge")
            )
        }
    )
}

package com.doool.download

import com.doool.core.base.BaseViewModel
import com.doool.pokedex.domain.LoadState
import com.doool.pokedex.domain.usecase.DownloadStaticData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val downloadStaticData: DownloadStaticData
) : BaseViewModel() {

    fun download(): StateFlow<LoadState<Unit>> =
        downloadStaticData().stateInWhileSubscribed { LoadState.Loading() }
}

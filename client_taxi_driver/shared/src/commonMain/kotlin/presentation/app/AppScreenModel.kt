package presentation.app

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import domain.usecase.ILoginUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppScreenModel(private val manageUser: ILoginUserUseCase): ScreenModel {
    private val _isKeptLoggedIn: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isKeptLoggedIn: StateFlow<Boolean?> = _isKeptLoggedIn.asStateFlow()

    init {
        getIsKeptLoggedIn()
    }
    private fun getIsKeptLoggedIn(){
         coroutineScope.launch(Dispatchers.IO) {
            val isKeptLoggedIn = manageUser.getKeepMeLoggedInFlag()
            if (!isKeptLoggedIn) {
                manageUser.saveUsername("")
            }
            _isKeptLoggedIn.update { isKeptLoggedIn }
        }
    }
}
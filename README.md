To review:


RemoteDS : RepoDs

LocalDS : RepoDs



class Repository (
val remoteDs : RepoDs,
val localDs : RepoDs) {
	fun getWord(word : String) : String = localDs.get(word) ?: remoteDs.getWord(word)
}


class RemoteDS (private val api : ApiInterface): RepoDs{
	
	override fun getWord (word : String) : String = apiCall { api.getWord(word) }
}


class LocalDS ( dataStore : DataStore) : RepoDs {
	override fun getWord (word : String) : String = dataStore.data.first().let { prefs -> prefs.get(KEY)}
}

fun apiCall<T>( call : Suspend.() -> Unit) : Response<T>  = try {
	call()?.let {
		if (it.isSuccess){
		Result.Success(body(), it.code)
		} else Result.Error (body().error, it.code)
	} ?: Result.Error(default error)
} catch {
	Result.Error(default error)
}


class BViewwModel (
private val repo :Repository,
private val errorMapper : ErrorMapper ) : ViewModel {
	
	fun getWord(word : String) {
	viewModelscope.launch {

	when (val result = repo.getWord(word)) {
	is Result.Success -> uiState.setState {result.data}
	is Result.Error -> errorMapper.map(result.error).also { uiState.setState { error = it}}
	}
	}
	}

}



LocalDatSourceImpl : LocalDataSoure


private val _uiState : MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
val uiState : StateFlow<ViewState> = _uiState



onCreate(...){
	
	setContent {
	DictionaryScreen(viewModel = hiltViewModel())
	}
}


DictionaryScreen(viewModel : VM){
	val state by vm.collectAsLifeca..

	DictionaryScreen(state = state, events = viewModel::handleEvents)
}

DictionaryScreen(
state : UiState,
events : (Events) -> Unit){
	when(state){
	is State.Success -> DictionarySuccess(onClick = {events(...)})
	is State.Error -> DictionaryError()
	}
}

package com.example.cocktailapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cocktailapp.CocktailAppApplication
import com.example.cocktailapp.data.repositories.CocktailRepository
import com.example.cocktailapp.data.states.CocktailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(private val cocktailRepository: CocktailRepository): ViewModel() {
    private val _uiState = MutableStateFlow(listOf<CocktailUiState>())
    val uiState: StateFlow<List<CocktailUiState>> = _uiState.asStateFlow()

    private fun getCocktailsList(){
        viewModelScope.launch {
            cocktailRepository.getCocktailsList().collect{ list ->
                println(list)
                val listUi = list.map{
                    CocktailUiState(it.name, it.thumb)
                }
                _uiState.emit(listUi)
            }
        }
    }
    init {
        getCocktailsList()
    }
}
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class CocktailViewModel(private val cocktailRepository: CocktailRepository): ViewModel() {
    private val _uiState = MutableStateFlow(listOf<CocktailUiState>())
    val uiState: StateFlow<List<CocktailUiState>> = _uiState.asStateFlow()

    private fun getCocktails(){
        viewModelScope.launch {
            try {
                val listCocktail = cocktailRepository.getCocktails()
                _uiState.emit(listCocktail.map{
                    CocktailUiState(name = it.name)
                })
            }catch (e: Exception){

            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[APPLICATION_KEY] as CocktailAppApplication)
                val cocktailRepository = app.container.cocktailRepository
                CocktailViewModel(cocktailRepository)
            }
        }
    }
}
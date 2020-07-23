package com.example.pokedexapplication.viewModel

import android.util.Log
import androidx.databinding.BaseObservable
import com.example.pokedexapplication.BR
import com.example.pokedexapplication.IEditextEventListener
import com.example.pokedexapplication.Store.State.AppState
import com.example.pokedexapplication.store
import org.rekotlin.StoreSubscriber

class AppStateViewModel(private var editTextEventListener: IEditextEventListener) :
  BaseObservable(), StoreSubscriber<AppState> {
  private var titleMainActivity: String = "Pokemon"

  private var searchQuery: String = ""

  init {
    store.subscribe(this) {
      it.select {
        it.appState
      }
    }
  }

  private fun setTitleMainActivity(title: String) {
    this.titleMainActivity = title
    notifyPropertyChanged(BR.mAppStateViewModel)
  }

  fun getTitleMainActivity() = this.titleMainActivity

  fun getEditextEventListener() = this.editTextEventListener

  private fun setSearchQuery(query: String) {
    this.searchQuery = query
    notifyChange()
  }

  fun getSearchQuery() = this.searchQuery

  fun checkSearchQueryIsBlank() = this.searchQuery.isBlank()

  override fun newState(state: AppState) {
    state.apply {
      setTitleMainActivity(titleMainActivity)
      setSearchQuery(searchQuery)
    }
  }

  companion object {

  }

}
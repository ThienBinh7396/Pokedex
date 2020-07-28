package com.example.pokedexapplication.viewModel

import android.util.Log
import android.view.LayoutInflater
import android.widget.TableLayout
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.example.pokedexapplication.model.PokemonBasicStat
import com.example.pokedexapplication.model.PokemonDetail
import com.example.pokedexapplication.R
import com.example.pokedexapplication.databinding.TableRowPokemonBasicStatBinding

class PokemonDetailViewModel : BaseObservable() {
  private var pokemonDetail: PokemonDetail? = null
  private var mIsLoading: Boolean = true

  val stats
    get() = this.pokemonDetail?.stats

  val evolutions
    get() = this.pokemonDetail?.evolutions

  val moves
    get() = this.pokemonDetail?.moves

  val pokemon
    get() = this.pokemonDetail?.pokemon
  val pokemonTypes
    get() = this.pokemonDetail?.pokemon?.pokemonTypes

  val pokemonWeaknesses
    get() = this.pokemonDetail?.stats?.weaknesses

  val pokemonIdText
    get() = "#${this.pokemonDetail?.pokemon?.id}"

  val isLoading
    get() = this.mIsLoading

  fun setIsLoading(isLoading: Boolean) {
    this.mIsLoading = isLoading
    notifyChange()
  }

  fun setPokemonDetail(pokemonDetail: PokemonDetail?) {
    this.pokemonDetail = pokemonDetail
    notifyChange()
  }

  companion object {
    @BindingAdapter("app:bindTableRowPokemonStat")
    @JvmStatic
    fun bindTableRowPokemonStat(
      tableLayout: TableLayout,
      pokemonStats: MutableList<PokemonBasicStat>?
    ) {
      tableLayout.removeAllViews()

      pokemonStats?.forEach {
        val mTableRowPokemonBasicStatBinding =
          DataBindingUtil.inflate<TableRowPokemonBasicStatBinding>(
            LayoutInflater.from(tableLayout.context),
            R.layout.table_row_pokemon_basic_stat,
            null,
            false
          )

        mTableRowPokemonBasicStatBinding.mPokemonBaseicStatsViewModel =
          PokemonBaseicStatsViewModel(it)

        tableLayout.addView(mTableRowPokemonBasicStatBinding.root)
      }

      tableLayout.requestLayout()
    }
  }
}
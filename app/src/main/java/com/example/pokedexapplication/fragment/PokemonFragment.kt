package com.example.pokedexapplication.fragment

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.pokedexapplication.DetailActivity
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.PokemonState
import com.example.pokedexapplication.adapter.RecyclerView.PokemonItemAdapter
import com.example.pokedexapplication.common.NumberUtils.Companion.covertDpToPixels
import com.example.pokedexapplication.databinding.FragmentPokemonBinding
import com.example.pokedexapplication.dialog.PokemonShortInformationDialog
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.PokemonFragmentViewModel
import org.rekotlin.StoreSubscriber


class PokemonFragment :
  Fragment(),
  StoreSubscriber<PokemonState>,
  PokemonItemAdapter.PokemonItemEventListener,
  PokemonShortInformationDialog.IInformationPokemonDialogOnDimissListener {

  private lateinit var mFragmentPokemonBinding: FragmentPokemonBinding

  private lateinit var mPokemonFragmentViewModel: PokemonFragmentViewModel

  private lateinit var mLinearLayoutManager: LinearLayoutManager

  private lateinit var mPokemonItemAdapter: PokemonItemAdapter

  private lateinit var mPokemonDialog: PokemonShortInformationDialog

  private var mPokemonItemMapIconShowWithRect: MutableMap<Int, Rect> = mutableMapOf()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    mFragmentPokemonBinding = DataBindingUtil.inflate(
      inflater,
      R.layout.fragment_pokemon,
      container,
      false
    )

    mPokemonFragmentViewModel = PokemonFragmentViewModel()

    mFragmentPokemonBinding.mPokemonFragmentViewModel = mPokemonFragmentViewModel

    mLinearLayoutManager = LinearLayoutManager(context)
    mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL

    mFragmentPokemonBinding.rcvPokemon.layoutManager = mLinearLayoutManager

    mPokemonItemAdapter = PokemonItemAdapter(this)

    mFragmentPokemonBinding.rcvPokemon.adapter = mPokemonItemAdapter

    mPokemonDialog = PokemonShortInformationDialog(activity as AppCompatActivity, this)

    eventListener()

    return mFragmentPokemonBinding.pokemonFragment
  }

  private fun eventListener() {
    mFragmentPokemonBinding.rcvPokemon.addOnItemTouchListener(object :
      RecyclerView.OnItemTouchListener {
      override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
      }

      override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val viewSwipedRight =
          rv.findChildViewUnder(e.x - rv.width, e.y)
        if (viewSwipedRight != null && e.action == MotionEvent.ACTION_DOWN) {

          Log.d("Binh", "${viewSwipedRight.tag} ${rv.width} ${e.x} ${e.y}")

          var findIconShowPosition = -1

          for ((position, rect) in mPokemonItemMapIconShowWithRect) {
            if (rect.top < e.y && rect.bottom > e.y && rect.left < e.x && rect.right > e.x) {
              findIconShowPosition = position
            }
          }

          if (findIconShowPosition > -1) {
            navigateToPokemonDetail(store.state.pokemonState.pokemons[findIconShowPosition].id)
          }
        }

        return false
      }

      override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
      }

    })

    mFragmentPokemonBinding.rcvPokemon.addOnScrollListener(object : OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(1)) {
          store.state.appState.let { appState ->
            store.state.pokemonState.apply {
              val listSize =
                if (appState.isSearching) searchPokemons.size else pokemons.size
              val total = if (appState.isSearching) searchTotal else total

              if (listSize >= total) {
                Toast.makeText(activity, "No pokemon more...", Toast.LENGTH_SHORT).show()
              } else {
                store.dispatch(
                  PokemonAction.FETCH_DATA(
                    name = if (appState.isSearching) appState.searchQuery else "",
                    page = listSize / 20 + 1,
                    isSearching = appState.isSearching
                  )
                )
              }
            }
          }
        }
      }
    })
    swipeToShowDetailPokemon()
  }

  override fun newState(state: PokemonState) {
    state.apply {
      mPokemonFragmentViewModel.setPokemonList(pokemons)
      mPokemonFragmentViewModel.setIsLoading(isLoadingPokemons)
      mPokemonFragmentViewModel.setSearchPokemonList(searchPokemons)
    }
  }

  override fun onStart() {
    super.onStart()

    store.subscribe(this) {
      it.select { it ->
        it.pokemonState
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()

    Log.d("Binh", "Destroy ${store.state.pokemonState.pokemons.size}")

    store.unsubscribe(this)
  }

  override fun onItemClickListener(position: Int, pokemonId: String) {
    mPokemonItemAdapter.updateIndexSelected(position)
    mPokemonDialog.showDialog(position, pokemonId)
  }

  override fun onDimissListener(positionPokemon: Int) {
    mPokemonItemAdapter.updateIndexSelected(positionPokemon)
  }

  private fun getBitmap(context: Context, vectorDrawableId: Int): Bitmap? {
    val bitmap: Bitmap?
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
      val vectorDrawable: Drawable = context.getDrawable(vectorDrawableId)!!
      bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
      )
      val canvas = Canvas(bitmap)
      vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
      vectorDrawable.draw(canvas)
    } else {
      bitmap = BitmapFactory.decodeResource(context.resources, vectorDrawableId)
    }
    return bitmap
  }

  private fun swipeToShowDetailPokemon() {
    //Swipe to delete currentTodo Item
    ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
      10,
      ItemTouchHelper.LEFT
    ) {
      private val background = ColorDrawable(Color.parseColor("#dfdfdf"))

      private var iconGoDetail: Drawable =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          resources.getDrawable(R.drawable.ic_baseline_zoom_in_24, null)
        } else {
          resources.getDrawable(R.drawable.ic_baseline_zoom_in_24)
        }

      private val iconSize = covertDpToPixels(context!!, 16)

      init {
        DrawableCompat.setTint(iconGoDetail, R.color.colorPrimary)
      }

      override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
      ): Boolean {
        return false
      }

      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition

        val bounds = iconGoDetail.bounds

        mPokemonItemMapIconShowWithRect.put(position, bounds)

        Log.d("Binh", "Icon bound: ${bounds.left} ${bounds.top} ${bounds.right} ${bounds.bottom}")
      }

      override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean
      ) {
        try {
          if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView

            itemView.translationX = dX / 6

            when {
              dX < 0 -> { // Swiping to the right
                background.setBounds(
                  (itemView.right + dX / 6).toInt(),
                  itemView.top + 2,
                  itemView.right,
                  itemView.bottom - 2
                )

                val height = itemView.height

                iconGoDetail.setBounds(
                  (itemView.right - iconSize + itemView.right / 12 + dX / 6).toInt(),
                  (itemView.top.toFloat() + height / 2 - iconSize).toInt(),
                  (itemView.right + iconSize + itemView.right / 12 + dX / 6).toInt(),
                  (itemView.bottom.toFloat() - height / 2 + iconSize).toInt()
                )
                background.draw(c)
                iconGoDetail.draw(c)
              }
              else -> {
                background.setBounds(
                  0,
                  0,
                  0,
                  0
                )
                background.draw(c)
              }
            }

          } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
          }
        } catch (e: Exception) {
          Log.d("Binh", "DX: ${e.message}")

          e.printStackTrace()
        }
      }
    }).attachToRecyclerView(mFragmentPokemonBinding.rcvPokemon)
  }

  private fun navigateToPokemonDetail(pokemonId: String) {
    Log.d("Binh", "Navigate: $pokemonId")

    val intent = Intent(activity, DetailActivity::class.java)
    intent.putExtra("data", pokemonId)
    intent.putExtra("flag", "Pokemon")

    startActivity(intent)
  }
}
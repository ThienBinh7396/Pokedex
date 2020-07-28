package com.example.pokedexapplication.dialog

import android.app.Dialog
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedexapplication.R
import com.example.pokedexapplication.Store.Action.PokemonAction
import com.example.pokedexapplication.Store.State.PokemonState
import com.example.pokedexapplication.adapter.RecyclerView.PokemonTypeItemAdapter
import com.example.pokedexapplication.common.BitmapUtils
import com.example.pokedexapplication.databinding.DialogPokemonShortInformationBinding
import com.example.pokedexapplication.store
import com.example.pokedexapplication.viewModel.PokemonDetailViewModel
import org.rekotlin.StoreSubscriber

class PokemonShortInformationDialog(
  var activity: AppCompatActivity,
  dialogEventListenter: IInformationPokemonDialogOnDimissListener
) : Dialog(activity), StoreSubscriber<PokemonState> {
  private var dialog: Dialog? = null

  private var mDialogPokemonShortInformationBinding: DialogPokemonShortInformationBinding

  private var mPokemonDetailViewModel: PokemonDetailViewModel

  private var mPokemonTypeLinearLayoutManager: LinearLayoutManager

  private var handleOnDimissListener: IInformationPokemonDialogOnDimissListener? = null

  private var positionPokemonShowing: Int = -1

  private var rootView: View

  private var containerView: View
  private var overlapArea: View
  private var dialogContent: View

  private var windowLayoutParams: WindowManager.LayoutParams

  private var enterAnimation: Animation
  private var exitAnimation: Animation

  private var handler = Handler(Looper.getMainLooper())

  init {
    enterAnimation = AnimationUtils.loadAnimation(context, R.anim.enter_pokemon_dialog_content_anim)
    exitAnimation = AnimationUtils.loadAnimation(context, R.anim.exit_pokemon_dialog_content_anim)

    handleOnDimissListener = dialogEventListenter

    mDialogPokemonShortInformationBinding = DataBindingUtil.inflate(
      LayoutInflater.from(activity),
      R.layout.dialog_pokemon_short_information,
      null,
      false
    )

    mPokemonDetailViewModel = PokemonDetailViewModel()

    mDialogPokemonShortInformationBinding.mPokemonDetailViewModel = mPokemonDetailViewModel

    mPokemonTypeLinearLayoutManager = LinearLayoutManager(activity)
    mPokemonTypeLinearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
    mDialogPokemonShortInformationBinding.rcvPokemonType.layoutManager =
      mPokemonTypeLinearLayoutManager


    dialog = Dialog(activity)
    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog?.setCancelable(false)

    rootView = mDialogPokemonShortInformationBinding.root

    dialog?.setContentView(rootView)

    windowLayoutParams = WindowManager.LayoutParams()
    windowLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
    windowLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
    windowLayoutParams.gravity = Gravity.CENTER
    dialog?.window?.attributes = windowLayoutParams

    containerView = dialog?.findViewById(R.id.containerView)!!

    overlapArea = dialog?.findViewById(R.id.overlapArea)!!

    dialogContent = dialog?.findViewById(R.id.dialog_content)!!

    dialogContent.setOnClickListener {
    }

    overlapArea.setOnClickListener {
      setContentViewAnimation(false)

      handler.postDelayed({
        dialog?.dismiss()
      }, 200)

    }

    dialog?.setOnDismissListener {
      handleDimissDialog()
    }

    store.subscribe(this) {
      it.select { it ->
        it.pokemonState
      }
    }

  }

  private fun setContentViewAnimation(isShow: Boolean) {
    dialogContent.visibility = if (isShow) View.VISIBLE else View.GONE

    dialogContent.startAnimation(if (isShow) enterAnimation else exitAnimation)
  }

  fun showDialog(positionPokemon: Int, pokemonId: String) {

    this.positionPokemonShowing = positionPokemon

    blurBackgroundBehind()

    handler.postDelayed({
      dialog?.show()
      setContentViewAnimation(true)
      Log.d("Binh", "dialog. show")
    }, 150)

    store.dispatch(PokemonAction.FETCH_POKEMON_DETAIL(pokemonId))
  }

  private fun handleDimissDialog() {
    if (positionPokemonShowing > -1) {
      handleOnDimissListener?.onDimissListener(positionPokemonShowing)
    }

    positionPokemonShowing = -1
  }

  private fun blurBackgroundBehind() {
    var bitmap = BitmapUtils.blurActivityView(activity, 25f)

    dialog?.window?.setBackgroundDrawable(BitmapDrawable(activity.resources, bitmap))
  }

  override fun newState(state: PokemonState) {
    if (dialog != null) {
      state.apply {
        mPokemonDetailViewModel.setIsLoading(isLoadingPokemonDetail)
        mPokemonDetailViewModel.setPokemonDetail(pokemonDetail)
      }
    }
  }

  interface IInformationPokemonDialogOnDimissListener {
    fun onDimissListener(positionPokemon: Int)
  }
}
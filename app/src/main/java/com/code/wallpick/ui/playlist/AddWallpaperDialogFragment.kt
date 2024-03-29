package com.code.wallpick.ui.playlist

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.code.wallpick.R

class AddWallpaperDialogFragment : DialogFragment() {

    lateinit var spaceCard: CardView
    lateinit var amoledCard: CardView
    lateinit var animeCard: CardView
    lateinit var carsCard: CardView

    lateinit var addButton: TextView
    lateinit var cancelButton: TextView

    private lateinit var playlistName: String
    var selectedCollection: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val v = inflater.inflate(R.layout.dialog_add_wallpaper, container)
        spaceCard = v.findViewById(R.id.space_card)
        amoledCard = v.findViewById(R.id.amoled_card)
        animeCard = v.findViewById(R.id.anime_card)
        carsCard = v.findViewById(R.id.cars_card)
        addButton = v.findViewById(R.id.positive_button)
        cancelButton = v.findViewById(R.id.negative_button)
        return v
    }

    override fun onResume() {
        super.onResume()
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        playlistName = bundle!!.getString("TEXT", "")

        initCardClickListener()
        initButtonClickListener()
    }

    private fun initButtonClickListener() {
        cancelButton.setOnClickListener {
            dismiss()
        }
        addButton.setOnClickListener {
            val intent = Intent(context,WallpaperActivity::class.java)
            intent.putExtra("collection",selectedCollection)
            intent.putExtra("playlist",playlistName)
            startActivity(intent)
            dismiss()
        }
        addButton.isClickable = false
        addButton.isFocusable = false
    }

    private fun initCardClickListener() {

        spaceCard.setOnClickListener {
            selectedCollection = arrayOf("galaxy","universe").random()
            cardSelected(it as CardView)
        }
        amoledCard.setOnClickListener {
            selectedCollection = "black background"
            cardSelected(it as CardView)
        }
        animeCard.setOnClickListener {
            selectedCollection = "japan" //arrayOf("japan","tokyo night").random()
            cardSelected(it as CardView)
        }
        carsCard.setOnClickListener {
            selectedCollection = arrayOf("sports car","luxury car","cars").random()
            cardSelected(it as CardView)
        }
    }

    private fun cardSelected(card: CardView) {
        spaceCard.alpha = 1f
        amoledCard.alpha = 1f
        animeCard.alpha = 1f
        carsCard.alpha = 1f

        card.alpha = 0.5f
        addButton.alpha = 1f
        addButton.isClickable = true
        addButton.isFocusable = true
    }

    companion object {
        const val TAG = "AddWallpaperDialog"
    }

}
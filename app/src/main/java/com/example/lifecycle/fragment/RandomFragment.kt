package com.example.lifecycle.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.lifecycle.databinding.FragmentRandomBinding
import com.example.lifecycle.navigator
import java.util.*
import kotlin.properties.Delegates
import kotlin.random.Random

class RandomFragment : Fragment() {

    private val TAG = "RandomFragment ${Random.nextInt(0, 100)}"

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = requireNotNull(_binding)

    // fragment's state
    private var backgroundColor by Delegates.notNull<Int>()
    private lateinit var textRandomNumber: String

    // getters
    private val textColor: Int get() = if (ColorUtils.calculateLuminance(backgroundColor) > 0.3)
        Color.BLACK
    else
        Color.WHITE

    private var uuidArgument: String
        get() = requireArguments().getString(ARG_UUID)!!
        set(value) = requireArguments().putString(ARG_UUID, value)

    // ---

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getUuid()
        Log.d(TAG, "$uuidArgument: onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "$uuidArgument: onCreate bundle = $savedInstanceState")

        // init|restore state
        backgroundColor = savedInstanceState?.getInt(KEY_BACKGROUND_COLOR) ?: getRandomColor()
        textRandomNumber = savedInstanceState?.getString(KEY_RANDOM_NUMBER) ?: getRandomNumber()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Log.d(TAG, "$uuidArgument: onCreateView")

        return FragmentRandomBinding.inflate(inflater, container, false)
            .also {
                _binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi() // setup button listeners
        updateUi() // render state

        Log.d(TAG, "$uuidArgument: onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "$uuidArgument: onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "$uuidArgument: onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "$uuidArgument: onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "$uuidArgument: onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "$uuidArgument: onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "$uuidArgument: onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "$uuidArgument: onDetach")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_RANDOM_NUMBER, textRandomNumber)
        outState.putInt(KEY_BACKGROUND_COLOR, backgroundColor)
        Log.d(TAG, "$uuidArgument: onSaveInstanceState")
    }

    fun getUuid(): String {
        return uuidArgument
    }

    fun onNewScreenNumber(number: Int) {
        binding.countTextView.text = "Number: $number"
    }

    private fun setupUi() {
        binding.changeUuidButton.setOnClickListener {
            uuidArgument = UUID.randomUUID().toString()
            updateUi()
        }
        binding.changeBackgroundButton.setOnClickListener {
            backgroundColor = getRandomColor()
            updateUi()
        }
        binding.changeChuckNorrisFactButton.setOnClickListener {
            textRandomNumber = getRandomNumber()
            updateUi()
        }
        binding.launchNextButton.setOnClickListener {
            navigator().pushNext()
        }
    }

    private fun updateUi() {
        binding.numberTextView.text = textRandomNumber
        binding.uuidTextView.text = uuidArgument

        binding.root.setBackgroundColor(backgroundColor)
        binding.uuidTextView.setTextColor(textColor)
        binding.numberTextView.setTextColor(textColor)
        binding.countTextView.setTextColor(textColor)
    }

    private fun getRandomColor(): Int = -Random.nextInt(0xFFFFFF)

    private fun getRandomNumber(): String = Random.nextInt(0, 1000).toString()

    companion object {
        private const val ARG_UUID = "ARG_UUID"

        private const val KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR"
        private const val KEY_RANDOM_NUMBER = "KEY_RANDOM_NUMBER"

        fun newInstance(uuid: String) = RandomFragment().apply {
            arguments = bundleOf(ARG_UUID to uuid)
        }
    }

}
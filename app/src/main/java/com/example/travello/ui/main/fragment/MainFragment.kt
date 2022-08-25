package com.example.travello.ui.main.fragment

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.travello.R
import com.example.travello.databinding.FragmentMainBinding
import com.example.travello.ui.main.speech.RecognitionUtil
import com.example.travello.ui.main.speech.RecognitionUtil.Companion.getErrorText
import com.example.travello.ui.main.viewmodel.PoiViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment(), RecognitionListener {

    companion object {
        const val TAG = "Main Fragment"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private val viewModel: PoiViewModel by sharedViewModel()

    private lateinit var recognitionUtil: RecognitionUtil
    private lateinit var cityEt: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var stateTv: TextView

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setRecognition()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRecognition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater,
            R.layout.fragment_main, container, false
        )

        binding.micBtn.setOnClickListener {
            if (recognitionUtil.isInProgress) {
                recognitionUtil.stopListening()
            } else {
                recognitionUtil.startListening()
            }
        }

        binding.searchBtn.setOnClickListener {
            viewModel.city(cityEt.text.toString())
            requireActivity().supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.container, ListFragment.newInstance(), ListFragment.TAG)
                addToBackStack(ListFragment.TAG)
            }
        }

        cityEt = binding.cityEt
        progressBar = binding.speechProgressBar
        stateTv = binding.stateTv

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recognitionUtil.destroySpeechRecognizer()
    }

    override fun onReadyForSpeech(bundle: Bundle?) {
        Log.i(TAG, "onBeginningOfSpeech")
        progressBar.isIndeterminate = false
        progressBar.max = 10
        stateTv.text = resources.getString(R.string.tap_to_speak)
        stateTv.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        cityEt.setText("")
    }

    override fun onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech")
    }

    override fun onRmsChanged(rms: Float) {
        Log.i(TAG, "onRmsChanged $rms")
        progressBar.progress = rms.toInt()
    }

    override fun onBufferReceived(bytes: ByteArray?) {
        Log.i(TAG, "onBufferReceived: $bytes")
    }

    override fun onEndOfSpeech() {
        Log.i(TAG, "onEndOfSpeech")
        progressBar.isIndeterminate = true
    }

    override fun onError(errorCode: Int) {
        val errorMessage: String = getErrorText(errorCode)
        Log.d(TAG, "FAILED $errorMessage")
        stateTv.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        stateTv.text = errorMessage
    }

    override fun onResults(results: Bundle?) {
        Log.i(TAG, "onResults")
        stateTv.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        cityEt.setText(data?.get(0).toString())
    }

    override fun onPartialResults(bundle: Bundle?) {
        recognitionUtil.stopListening()
        Log.i(TAG, "onPartialResults")
    }

    override fun onEvent(event: Int, bundle: Bundle?) {
        Log.i(TAG, "onEvent")
    }

    private fun setRecognition() {
        recognitionUtil = RecognitionUtil(requireActivity(), this)
        recognitionUtil.setUpSpeechRecognizer()
    }
}
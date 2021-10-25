package net.kurdsofts.weatherapplication.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import net.kurdsofts.weatherapplication.R
import net.kurdsofts.weatherapplication.databinding.FragmentMainBinding

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class MainFragment
constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_main) {

    private lateinit var binder: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder = FragmentMainBinding.bind(view)
        view.layoutDirection = View.LAYOUT_DIRECTION_LTR

//        binder.timeTextView.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_ringerFragment)
//        }

        var location = "Inter Location"
        binder.locationNameTextView.text = location
        binder.getDataButton.setOnClickListener {
            location = binder.locationEditText.text.toString()
            binder.locationNameTextView.text = location
            getTimeZone(location)
            getWeather(location)
        }

    }

    private fun getTimeZone(location: String) {
        if (location == "") {
            Toast.makeText(requireContext(), "Please Inter City Name", Toast.LENGTH_LONG).show()
        } else {
            viewModel.getTimeZone(location)
            lifecycleScope.launchWhenStarted {
                viewModel.time.collect { event ->
                    when (event) {
                        is MainViewModel.TimeZoneEvent.Success -> {
                            binder.mainProgressBar.visibility = View.GONE
                            val locationData = event.result
                            binder.timeTextView.text = locationData.localtime
                        }
                        is MainViewModel.TimeZoneEvent.Failure -> {
                            binder.mainProgressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "error: ${event.errorText}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is MainViewModel.TimeZoneEvent.Loading -> {
                            binder.mainProgressBar.visibility = View.VISIBLE
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun getWeather(location: String) {
        if (location == "") {
            Toast.makeText(requireContext(), "Please Inter City Name", Toast.LENGTH_LONG).show()
        } else {
            viewModel.getWeather(location)
            lifecycleScope.launchWhenStarted {
                viewModel.weather.collect { event ->
                    when (event) {
                        is MainViewModel.WeatherEvent.Success -> {
                            binder.mainProgressBar.visibility = View.GONE
                            val locationWeather = event.result
                            binder.weatherTextView.text = "${locationWeather.current.temp_c} C"
                        }
                        is MainViewModel.WeatherEvent.Failure -> {
                            binder.mainProgressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "error: ${event.errorText}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is MainViewModel.WeatherEvent.Loading -> {
                            binder.mainProgressBar.visibility = View.VISIBLE
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun getCurrent(location: String) {
        if (location == "") {
            Toast.makeText(requireContext(), "Please Inter City Name", Toast.LENGTH_LONG).show()
        } else {
            viewModel.getCurrent(location)
            lifecycleScope.launchWhenStarted {
                viewModel.current.collect {
                    when (it) {
                        is MainViewModel.CurrentEvent.Loading -> {
                            binder.mainProgressBar.visibility = View.VISIBLE
                        }
                        is MainViewModel.CurrentEvent.Failure -> {
                            binder.mainProgressBar.visibility = View.GONE
                            binder.timeTextView.text = it.errorText
                        }
                        is MainViewModel.CurrentEvent.Success -> {
                            binder.mainProgressBar.visibility = View.GONE
                            binder.timeTextView.text = it.result.current.last_updated
                        }
                        else -> Unit
                    }
                }
            }
        }
    }


}
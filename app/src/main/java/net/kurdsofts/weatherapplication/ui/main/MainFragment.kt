package net.kurdsofts.weatherapplication.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import net.kurdsofts.weatherapplication.R
import net.kurdsofts.weatherapplication.data.model.models.Condition
import net.kurdsofts.weatherapplication.data.model.models.Current
import net.kurdsofts.weatherapplication.data.model.sealed_models.CurrentEvent
import net.kurdsofts.weatherapplication.data.model.sealed_models.ForecastEvent
import net.kurdsofts.weatherapplication.databinding.FragmentMainBinding
import net.kurdsofts.weatherapplication.util.DateTime

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class MainFragment
constructor(
    private val glide: RequestManager
) : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.layoutDirection = View.LAYOUT_DIRECTION_LTR
        bindingView()
        getCurrent("Mahabad")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindingView() {

//        ImageView
        glide.load(R.drawable.weather_background_image)
            .into(binding.mainBackgroundImageView)
        glide.load(R.drawable.ic_location)
            .into(binding.locationImageView)
        glide.load(R.drawable.ic_add)
            .into(binding.addingLocationImageView)

//        onImagesClicking
        binding.addingLocationImageView.setOnClickListener {
            Toast
                .makeText(
                    requireContext(),
                    "Want to add locations ?",
                    Toast.LENGTH_LONG
                )
                .show()
        }

//        TextView
        binding.dateTextView.text = DateTime.getDatAndTime()

//        Layout
        binding.loadingLayout.visibility = View.GONE
    }

    private fun getCurrent(location: String) {
        if (location == "") {
            Toast.makeText(requireContext(), "Please Inter City Name", Toast.LENGTH_LONG).show()
        } else {
            binding.locationTextView.text = location
            viewModel.getCurrentFromRetrofit(location)
            lifecycleScope.launchWhenStarted {
                viewModel.current.collect { event ->
                    when (event) {
                        is CurrentEvent.Loading -> {
                            binding.loadingLayout.visibility = View.VISIBLE
                        }
                        is CurrentEvent.Failure -> {
                            binding.loadingLayout.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "error: ${event.errorText}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is CurrentEvent.Success -> {
                            binding.loadingLayout.visibility = View.GONE
                            val currentData: Current = event.result.current
//                            val locationData: Location = event.result.location
                            val condition: Condition = currentData.condition
                            val imageUri = "http:${condition.icon}"
                            binding.weatherTempTextView.text = "${currentData.temp_c}°ᶜ"
                            binding.conditionTextView.text = condition.text
                            glide.load(imageUri)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_foreground)
                                .centerCrop()
                                .into(binding.weatherImageView)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun getForecast(location: String, daysToForecast: Int, aqi: String, alerts: String) {
        if (location == "") {
            Toast.makeText(requireContext(), "Please Inter City Name", Toast.LENGTH_LONG).show()
        } else {
            viewModel.getForecastFromRetrofit(location, daysToForecast, aqi, alerts)
            lifecycleScope.launchWhenStarted {
                viewModel.forecast.collect { event ->
                    when (event) {
                        is ForecastEvent.Success -> {


                        }
                        is ForecastEvent.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "error: ${event.errorText}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is ForecastEvent.Loading -> {

                        }
                        else -> Unit
                    }
                }
            }
        }
    }

}

//        binder.timeTextView.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_ringerFragment)
//        }
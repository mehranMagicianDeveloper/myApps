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
import net.kurdsofts.weatherapplication.data.model.models.Condition
import net.kurdsofts.weatherapplication.data.model.models.Current
import net.kurdsofts.weatherapplication.data.model.models.Location
import net.kurdsofts.weatherapplication.data.model.sealed_models.CurrentEvent
import net.kurdsofts.weatherapplication.data.model.sealed_models.ForecastEvent
import net.kurdsofts.weatherapplication.data.model.sealed_models.TimeZoneEvent
import net.kurdsofts.weatherapplication.databinding.FragmentMainBinding
import net.kurdsofts.weatherapplication.util.DateTime

@SuppressLint("SetTextI18n")
@AndroidEntryPoint
class MainFragment
constructor(
    private val glide: RequestManager
) : Fragment(R.layout.fragment_main) {

    private lateinit var binder: FragmentMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder = FragmentMainBinding.bind(view)
        view.layoutDirection = View.LAYOUT_DIRECTION_LTR

//        binder.timeTextView.setOnClickListener {
//            findNavController().navigate(R.id.action_mainFragment_to_ringerFragment)
//        }

        bindViews()
        val mahabad = "mahabad"
        getCurrent(mahabad)

    }

    private fun bindViews() {

//        ImageView
        glide.load(R.drawable.weather_background_image)
            .into(binder.mainBackgroundImageView)
        glide.load(R.drawable.ic_location)
            .into(binder.locationImageView)

//        TextView
        binder.dateTextView.text = DateTime.getDatAndTime()

    }

    private fun getTimeZone(location: String) {
        if (location == "") {
            Toast.makeText(requireContext(), "Please Inter City Name", Toast.LENGTH_LONG).show()
        } else {
            viewModel.getTimeZone(location)
            lifecycleScope.launchWhenStarted {
                viewModel.time.collect { event ->
                    when (event) {
                        is TimeZoneEvent.Success -> {


//                            add progressbar visibility = gone
//                            and show data

                        }
                        is TimeZoneEvent.Failure -> {

//                            add progressbar visibility = gone

                            Toast.makeText(
                                requireContext(),
                                "error: ${event.errorText}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is TimeZoneEvent.Loading -> {

//                            add progressbar visibility = visible

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
            viewModel.getForecast(location, daysToForecast, aqi, alerts)
            lifecycleScope.launchWhenStarted {
                viewModel.weather.collect { event ->
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

    private fun getCurrent(location: String) {
        if (location == "") {
            Toast.makeText(requireContext(), "Please Inter City Name", Toast.LENGTH_LONG).show()
        } else {
            viewModel.getCurrent(location)
            lifecycleScope.launchWhenStarted {
                viewModel.current.collect { event ->
                    when (event) {
                        is CurrentEvent.Loading -> {

                        }
                        is CurrentEvent.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "error: ${event.errorText}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        is CurrentEvent.Success -> {
                            val current: Current = event.result.current
                            val location: Location = event.result.location
                            val condition: Condition = current.condition
                            val imageUri = "http:${condition.icon}"

                            binder.weatherTempTextView.text = "${current.temp_c}ᵒ°ᶜ"
                            binder.conditionTextView.text = condition.text
                            glide.load(imageUri)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.ic_launcher_foreground)
                                .centerCrop()
                                .into(binder.weatherImageView)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }


}
package com.example.speed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.speed.model.util.CountryResult
import com.example.speed.ui.composables.CountryCard
import com.example.speed.ui.theme.SpeedTheme
import com.example.speed.viewModel.CountryViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SpeedTheme {
                val viewModel = getViewModel<CountryViewModel>()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CountryScreen()
                }
            }
        }
    }
}

@Composable
fun CountryScreen() {
    val viewModel = koinViewModel<CountryViewModel>()
    val countriesResult by viewModel.countriesLiveData.observeAsState(initial = CountryResult.Loading)
    LaunchedEffect(Unit) {
        viewModel.getCountries()
    }
    when (countriesResult) {
        is CountryResult.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }

        is CountryResult.Success -> {
            val countries = (countriesResult as CountryResult.Success).data
            CountryCard(countries = countries)
        }

        is CountryResult.Failure -> {
            val exception = (countriesResult as CountryResult.Failure).exception
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(text = "Error: ${exception.localizedMessage}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GCountryScreenPreview() {
    SpeedTheme {
        CountryScreen()
    }
}
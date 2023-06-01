package com.example.speed.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.speed.model.data.Country

@Composable
fun CountryCard(countries: List<Country>) {
    LazyColumn {
        items(countries.size) { index ->
            val country = countries[index]
            Column(modifier = Modifier.padding(16.dp)) {
                Row {
                    Text(
                        text = "${country.name}, ${country.region}",
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = country.code)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = country.capital)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

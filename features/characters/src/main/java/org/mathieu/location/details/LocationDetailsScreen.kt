package org.mathieu.location.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.mathieu.characters.list.CharacterCard
import org.mathieu.location.LocationCard


private typealias UIState = LocationDetailsViewModel.LocationDetailsState

@Composable
fun LocationDetailsScreen(
    navController: NavController,
    locationId: Int
) {
    val viewModel: LocationDetailsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    viewModel.init(locationId = locationId)

    LocationDetailsContent(
        state = state,
        navController = navController
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationDetailsContent(
    state: UIState,
    navController: NavController
) {

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            state.location?.let { location ->
                LocationCard(
                    name = location.name,
                    type = location.type,
                    onClick = { /* No action needed */ }
                )
            }

            LazyColumn {
                items(state.characters) { character ->
                    CharacterCard(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("characterDetails/${character.id}")
                            },
                        character = character
                    )
                }
            }
        }
    }
}
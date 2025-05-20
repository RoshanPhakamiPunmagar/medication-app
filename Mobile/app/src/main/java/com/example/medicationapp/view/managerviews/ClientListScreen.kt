package com.example.medicationapp.view.managerviews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.medicationapp.model.Client
import com.example.medicationapp.viewmodel.ClientViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClientListScreen(clientViewModel: ClientViewModel = viewModel()) {
    val clients by clientViewModel.clientsLiveData.observeAsState(emptyList())
    val currentPage by clientViewModel.currentPage.observeAsState(0)
    val totalPages by clientViewModel.totalPages.observeAsState(1)

    // Trigger the initial data load
    LaunchedEffect(Unit) {
        clientViewModel.getClientsPaged(page = 0, size = 5)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(24.dp)) {

        Text("Clients", style = MaterialTheme.typography.headlineMedium)

        if (clients.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(clients) { client ->
                    ClientItem(client = client)
                }
             //Pagination controls
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (currentPage > 0) {
                            Button(
                                onClick = {
                                    clientViewModel.getClientsPaged(currentPage - 1, 5)
                                    clientViewModel.setCurrentPage(currentPage - 1)
                                }
                            ) {
                                Text("Previous")
                            }
                        }

                        if (currentPage < totalPages - 1) {
                            Button(
                                onClick = {
                                    clientViewModel.getClientsPaged(currentPage + 1, 5)
                                    clientViewModel.setCurrentPage(currentPage + 1)
                                }
                            ) {
                                Text("Load More")
                            }
                        }
                    }
                }

                }
            }
        }
    }

@Composable
fun ClientItem(client: Client) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${client.name}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "DOB: ${client.dob}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Phone: ${client.contact}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

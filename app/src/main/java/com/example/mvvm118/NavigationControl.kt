package com.example.mvvm118

import TampilDataView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvm118.model.JenisKelamin.jenisK
import com.example.mvvm118.ui.view.FormulirView
import com.example.mvvm118.viewmodel.SiswaViewModel

enum class Halaman {
    FORMULIR,
    TAMPILDATA
}

@Composable
fun NavigationControl(
    modifier: Modifier = Modifier,
    viewModel: SiswaViewModel = viewModel(),
    navHost: NavHostController = rememberNavController()
){
    Scaffold {
        isipadding ->
        val uiState by viewModel.statusUI.collectAsState()

        NavHost(modifier = Modifier.padding(isipadding),
            navController = navHost, startDestination = Halaman.FORMULIR.name
        ){
            composable(
                route = Halaman.FORMULIR.name
            ){
                val konteks = LocalContext.current
                FormulirView(
                    listJK = jenisK.map{isi ->
                        konteks.resources.getString(isi)
                    },
                    onSubmitClicked = {
                        viewModel.saveDataSiswa(it)
                        navHost.navigate(Halaman.TAMPILDATA.name)
                    }
                )
            }

            composable(route = Halaman.TAMPILDATA.name) {
                TampilDataView(uiState = uiState, onBackButton = {
                    navHost.popBackStack()
                })
            }
        }
    }
}
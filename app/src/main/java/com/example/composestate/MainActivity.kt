package com.example.composestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

import com.example.composestate.ui.theme.ComposeStateTheme


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HelloScreen()
                }
            }
        }
    }
}

class HelloViewModel : ViewModel() {
    private val _name : MutableLiveData<String> = MutableLiveData("")
    val name : LiveData<String> = _name

    fun onNameChange(newName: String) {
        _name.value = newName
    }
}


@Composable
fun HelloScreen(viewModel: HelloViewModel = viewModel()) {
    //var name : String by rememberSaveable{ mutableStateOf("") }
    val name : String by viewModel.name.observeAsState("")
    HelloContent(name = name, onNameChange = {viewModel.onNameChange(it)})
}

@Composable
fun HelloContent(name : String, onNameChange: (String) -> Unit) {
    
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (name.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Hello $name",
                style = MaterialTheme.typography.h5
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange
        )
    }
}


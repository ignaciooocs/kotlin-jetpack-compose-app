package com.example.navigationapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Device(val name: String = "", val id: String? = null)

class FetchViewModel: ViewModel() {
    private val _dataList = MutableStateFlow<List<Device>>(emptyList())
    val dataList: StateFlow<List<Device>> = _dataList

    private val db = FirebaseFirestore.getInstance()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            db.collection("devices")
                .get()
                .addOnSuccessListener { documents ->
                    val data = documents.map { document ->
                        Device(id = document.id, name = document.getString("name") ?: "")
                    }
                    _dataList.value = data
                }
                .addOnFailureListener { exception ->
                    // Manejar error
                    _dataList.value = emptyList()
                }
        }
    }
}
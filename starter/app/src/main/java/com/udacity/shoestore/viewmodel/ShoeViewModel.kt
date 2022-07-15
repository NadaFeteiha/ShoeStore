package com.udacity.shoestore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class ShoeViewModel : ViewModel() {

    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    private val _shoeSelected = MutableLiveData<Shoe>()


    init {
        _shoeList.value = mutableListOf()
        _shoeSelected.value = null
    }

    fun setSelectedShoe(index: Int) {
        _shoeSelected.value = if (index == -1) {
            null
        } else {
            _shoeList.value?.get(index)
        }
    }

    fun getSelectedShoe(): Shoe? {
        return _shoeSelected.value
    }

    fun updateSelectedShoe(shoe: Shoe) {
        _shoeSelected.value?.apply {
            name = shoe.name
            company = shoe.company
            size = shoe.size
            description = shoe.description
        }
    }

    fun addNewShoe(shoe: Shoe) {
        _shoeList.value?.add(shoe)
        _shoeList.postValue(_shoeList.value)
    }
}
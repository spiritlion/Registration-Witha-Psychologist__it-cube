package com.example.registrationwithapsychologist__itcube.CustomComposable.Schedule


data class MomentClass(val year : Int, val month: Int, val date : Int, val hour: Int, var state : MomentState = MomentState.Free) {
    enum class MomentState {
        Free,
        Recorded,
        Occupied,
    }
}
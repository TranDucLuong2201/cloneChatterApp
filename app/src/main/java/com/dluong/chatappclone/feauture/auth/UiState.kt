package com.dluong.chatappclone.feauture.auth

sealed interface UiState {
    data object Nothing : UiState
    data object Loading : UiState
    data object Success : UiState
    data class Error(val message: String) : UiState
}
package com.example.myapplication.core.ui.compose

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Suppress("EmptyFunctionBlock")
class NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {
        // детект ругается на пустой блок, но это бред, он и должен быть пустой
    }
    override fun tryEmit(interaction: Interaction) = true
}
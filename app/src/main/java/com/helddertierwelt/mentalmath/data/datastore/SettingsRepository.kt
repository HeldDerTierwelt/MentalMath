/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.helddertierwelt.mentalmath.SettingsStateProto
import com.helddertierwelt.mentalmath.presentation.viewmodel.SettingsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<SettingsStateProto> by dataStore(
    fileName = "settings.pb",
    serializer = SettingsStateSerializer
)

class SettingsRepository(
    private val context: Context
) {
    val settingsFlow: Flow<SettingsState> = context.settingsDataStore.data
        .map { proto -> proto.toSettingsState() }

    suspend fun saveSettings(settings: SettingsState) {
        context.settingsDataStore.updateData { currentProto ->
            settings.toProto()
        }
    }
}
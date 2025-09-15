/*
 * Copyright (C) 2025 HeldDerTierwelt
 * Licensed under the GNU General Public License v3.0
 * See LICENSE.md for details.
 */

package com.helddertierwelt.mentalmath.data.datastore

import androidx.datastore.core.Serializer
import com.helddertierwelt.mentalmath.SettingsStateProto
import com.helddertierwelt.mentalmath.presentation.viewmodel.SettingsState
import java.io.InputStream
import java.io.OutputStream

object SettingsStateSerializer : Serializer<SettingsStateProto> {
    override val defaultValue: SettingsStateProto = SettingsState().toProto()

    override suspend fun readFrom(input: InputStream): SettingsStateProto {
        return SettingsStateProto.parseFrom(input)
    }

    override suspend fun writeTo(t: SettingsStateProto, output: OutputStream) {
        t.writeTo(output)
    }
}
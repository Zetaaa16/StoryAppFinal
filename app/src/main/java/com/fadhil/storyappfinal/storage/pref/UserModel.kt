package com.fadhil.storyappfinal.storage.pref

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserModel(
    var token: String? = ""
) : Parcelable

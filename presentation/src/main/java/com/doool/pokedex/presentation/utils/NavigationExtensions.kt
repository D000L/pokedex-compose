package com.doool.pokedex.presentation.utils

import android.content.Context
import android.content.Intent
import com.doool.pokedex.presentation.ui.main.MainActivity

fun Context.goMain() {
    startActivity(
        Intent(this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    )
}

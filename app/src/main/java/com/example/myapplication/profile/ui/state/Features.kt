package com.example.myapplication.profile.ui.state

@Suppress("MagicNumber")
enum class Features(val flag: Int) {
    THEME(1 shl 0), // 1
    LANG(1 shl 1), // 2
    NOTIFICATIONS(1 shl 2), // 4
    FACE_ID(1 shl 3) // 8
}
package com.meteoro.network.data

import com.squareup.moshi.Json

data class GifImage(@Json(name = "preview_gif") val previewGif: GifImageData?)

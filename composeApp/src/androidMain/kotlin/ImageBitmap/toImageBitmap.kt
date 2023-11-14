package ImageBitmap

import PlatformContext
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual fun ByteArray.toImageBitmap(): ImageBitmap = toAndroidBitmap().asImageBitmap()

fun ByteArray.toAndroidBitmap(): Bitmap{
    return BitmapFactory.decodeByteArray(this, 0 ,size)
}

actual fun blurFilter(bitmap: ImageBitmap, context: PlatformContext): ImageBitmap {
    TODO("Not yet implemented")
}
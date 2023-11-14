package ImageBitmap

import PlatformContext
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.FilterTileMode
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageFilter
import org.jetbrains.skia.Paint

actual fun ByteArray.toImageBitmap(): ImageBitmap {
   return Image.makeFromEncoded(this).toComposeImageBitmap()
}

actual fun blurFilter(bitmap: ImageBitmap, context: PlatformContext): ImageBitmap {
    return applyBlurFilter(bitmap.asSkiaBitmap()).asComposeImageBitmap()
}

private fun applyBlurFilter(bitmap: Bitmap): Bitmap {
    val result = Bitmap().apply {
        allocN32Pixels(bitmap.width, bitmap.height)
    }

    val blur = Paint().apply {
        imageFilter = ImageFilter.makeBlur(12f, 12f, FilterTileMode.CLAMP)
    }

    Canvas(result).apply {
        saveLayer(null, blur)
        drawImageRect(Image.makeFromBitmap(bitmap), bitmap.bounds.toRect())
        restore()
        readPixels(result, 0, 0)
        close()
    }

    return result
}
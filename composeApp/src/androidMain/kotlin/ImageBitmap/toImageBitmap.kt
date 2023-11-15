package ImageBitmap

import PlatformContext
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap

actual fun ByteArray.toImageBitmap(): ImageBitmap = toAndroidBitmap().asImageBitmap()

fun ByteArray.toAndroidBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, size)
}

actual fun blurFilter(bitmap: ImageBitmap, context: PlatformContext): ImageBitmap {
    return applyBlurFilter(bitmap.asAndroidBitmap(), context.android).asImageBitmap()
}

private fun applyBlurFilter(bitmap: Bitmap, context: Context): Bitmap {
    val result = bitmap.copy(Bitmap.Config.ARGB_8888, true)
    val renderScript = RenderScript.create(context)
    val tmpIn = Allocation.createFromBitmap(renderScript, bitmap)
    val tmpOut = Allocation.createFromBitmap(renderScript, result)

    val theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.A_8(renderScript)).apply {
        setRadius(15f)
        setInput(tmpIn)
        forEach(tmpOut)
    }

    tmpOut.copyTo(result)
    return result
}
package ImageBitmap

import PlatformContext
import androidx.compose.ui.graphics.ImageBitmap

expect fun ByteArray.toImageBitmap(): ImageBitmap
expect fun blurFilter(bitmap: ImageBitmap, context: PlatformContext): ImageBitmap
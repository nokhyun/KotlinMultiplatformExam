import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

interface Font {
    @Composable
    fun Regular(): FontFamily

    @Composable
    fun SemiBold(): FontFamily

    @Composable
    fun Bold(): FontFamily
}

@Immutable
data object RubikFont : Font {

    @Composable
    override fun Regular(): FontFamily = FontFamily(
        font(
            "Rubik", "rubik_regular", FontWeight.Normal, FontStyle.Normal
        )
    )

    @Composable
    override fun SemiBold(): FontFamily = FontFamily(
        font(
            "Rubik", "rubik_medium", FontWeight.SemiBold, FontStyle.Normal
        )
    )

    @Composable
    override fun Bold(): FontFamily = FontFamily(
        font(
            "Rubik", "rubik_bold", FontWeight.Bold, FontStyle.Normal
        )
    )
}

interface Typo {
    val typography: Typography
        @Composable get
}

interface TextStyles {
    @Composable
    fun h1(): TextStyle

    @Composable
    fun h2(): TextStyle

    @Composable
    fun h3(): TextStyle

    @Composable
    fun h4(): TextStyle

    @Composable
    fun h5(): TextStyle

    @Composable
    fun h6(): TextStyle

    @Composable
    fun subtitle1(): TextStyle

    @Composable
    fun subtitle2(): TextStyle

    @Composable
    fun body1(): TextStyle

    @Composable
    fun body2(): TextStyle

    @Composable
    fun button(): TextStyle

    @Composable
    fun caption(): TextStyle

    @Composable
    fun overline(): TextStyle
}

@Immutable
data object Typography : TextStyles, Typo {
    @Composable
    override fun h1(): TextStyle = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 45.sp,
        letterSpacing = (-1.5).sp,
        fontFamily = RubikFont.Bold()
    )

    @Composable
    override fun h2(): TextStyle = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 35.sp,
        letterSpacing = (-0.5).sp,
        fontFamily = RubikFont.Bold()
    )

    @Composable
    override fun h3(): TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        letterSpacing = 0.sp,
        fontFamily = RubikFont.Bold()
    )

    @Composable
    override fun h4(): TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 25.sp,
        letterSpacing = 0.25.sp,
        fontFamily = RubikFont.Bold()
    )

    @Composable
    override fun h5(): TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.sp,
        fontFamily = RubikFont.Bold()
    )

    @Composable
    override fun h6(): TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
        fontFamily = RubikFont.SemiBold()
    )

    @Composable
    override fun subtitle1(): TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        fontFamily = RubikFont.SemiBold()
    )

    @Composable
    override fun subtitle2(): TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        fontFamily = RubikFont.SemiBold()
    )

    @Composable
    override fun body1(): TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        fontFamily = RubikFont.Regular()
    )

    @Composable
    override fun body2(): TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        fontFamily = RubikFont.Regular()
    )

    @Composable
    override fun button(): TextStyle = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp,
        fontFamily = RubikFont.Regular()
    )

    @Composable
    override fun caption(): TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        fontFamily = RubikFont.Regular()
    )

    @Composable
    override fun overline(): TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
        fontFamily = RubikFont.Regular()
    )

    override val typography: Typography
        @Composable get() = Typography(
            h1 = h1(),
            h2 = h2(),
            h3 = h3(),
            h4 = h4(),
            h5 = h5(),
            h6 = h6(),
            subtitle1 = subtitle1(),
            subtitle2 = subtitle2(),
            body1 = body1(),
            body2 = body2(),
            button = button(),
            caption = caption(),
            overline = overline()
        )
}
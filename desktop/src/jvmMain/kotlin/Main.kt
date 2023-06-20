import br.com.murilorcm.common.App
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            val dir = File("C:\\Users\\muril\\git\\env\\windo\\")

            App(dir)
        }
    }
}
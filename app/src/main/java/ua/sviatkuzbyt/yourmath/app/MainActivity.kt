package ua.sviatkuzbyt.yourmath.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ua.sviatkuzbyt.yourmath.app.ui.theme.YourMathTheme
import ua.sviatkuzbyt.yourmath.data.MyClassData
import ua.sviatkuzbyt.yourmath.domain.MyClassDomain

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YourMathTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val domain = MyClassDomain().data
                    val data = MyClassData().data
                    Greeting(
                        name = "$domain, $data, app",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YourMathTheme {
        Greeting("Android")
    }
}
package com.example.comp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import com.example.comp.ui.theme.CompTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CoilTest()
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CoilTest() {
    Box(
        modifier = Modifier
            .height(150.dp)
            .width(150.dp),
        contentAlignment = Alignment.Center
    ) {

        val painter = rememberImagePainter(
            data = "https://avatars.githubusercontent.com/u/14994036?v=4",
            builder = {
                placeholder(R.drawable.baseline_image_not_supported_24)
                error(R.drawable.baseline_error_outline_24)
                crossfade(2000)
                transformations(
//                    GrayscaleTransformation(),
//                    CircleCropTransformation(),
                    BlurTransformation(context = LocalContext.current),
                    RoundedCornersTransformation(50f)
                )

            }
        )

//        val painterState = painter.state
        Image(painter = painter, contentDescription = "Logo")

//        if(painterState is ImagePainter.State.Loading) {
//            CircularProgressIndicator()
//        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CompTheme {
        CoilTest()
    }
}
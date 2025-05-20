package com.pjlaapps.salmosversoaverso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pjlaapps.salmosversoaverso.ui.theme.SalmosVersoAVersoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaPrincipal(

            )
        }
    }
}



@Composable
fun Texto() {
    Text(
        text = "Bem-aventurado o homem que não anda segundo o conselho dos ímpios, nem se detém no caminho dos pecadores, nem se assenta na roda dos escarnecedores.",
        style = TextStyle(
            fontSize = 24.sp
        )
    )
}

@Composable
fun Botoes(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround

    ) {

        Button(
            onClick = { /*TODO*/ },
            modifier = modifier.padding(2.dp),
            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Magenta )

        ) {
            Text(
                text = "-5",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = modifier.padding(2.dp)
        ) {
            Text(
                text = "-1",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                    ))
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = modifier.padding(2.dp)
        ) {
            Text(
                text = "+1",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = modifier.padding(2.dp)            ,
            colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color.Magenta )
        ) {
            Text(
                text = "+5",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ))
        }
    }
}


@Composable
fun TelaPrincipal(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy (45.dp)  ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Titulo()
        Capitulo()
        VersoPosicao()
        Spacer(modifier = Modifier.size(30.dp))
        Texto()
        Spacer(modifier = Modifier.size(30.dp))
        Botoes()
    }
}

@Composable
fun Capitulo() {
    Text(
        text = "Salmo 1",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    )
}

@Composable
fun Titulo() {
    Text(
        "Salmos Verso a Verso",
                style = TextStyle(
                fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    )
    )
}

@Composable
fun VersoPosicao(modifier: Modifier = Modifier) {
    Row (
        modifier = modifier
            .height(60.dp)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text("Verso: 1",
            style = TextStyle(
                fontSize = 22.sp
            ))
        Spacer(modifier = Modifier.width(60.dp))
        Text("Posição: 1",
            style = TextStyle(
                fontSize = 22.sp
            ))
    }

}

@Preview(showBackground = true)
@Composable
fun VersoaVersoPreview() {
    TelaPrincipal()
}
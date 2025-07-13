package com.pjlaapps.salmosversoaverso

import android.content.Context
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaPrincipal(
                contexto = LocalContext.current
            )
        }
    }
}

val Context.dataStore by preferencesDataStore(name = "verso_prefs")

fun saveVersoAtual(context: Context, capitulo: Int, versiculo: Int) {
    val chave = "PSA_${capitulo}_${versiculo}"
    val dataStoreKey = stringPreferencesKey("verso_atual")
    CoroutineScope(Dispatchers.IO).launch {
        context.dataStore.edit { prefs ->
            prefs[dataStoreKey] = chave
        }
    }
}

fun loadVersoAtual(context: Context): String? {
    val dataStoreKey = stringPreferencesKey("verso_atual")
    var versoAtual: String? = null
    CoroutineScope(Dispatchers.IO).launch {
        context.dataStore.data.collect { prefs ->
            versoAtual = prefs[dataStoreKey]
        }
    }
    return versoAtual
}

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier, contexto: Context) {
    var capitulo = remember { mutableStateOf(1) }
    var versiculo = remember { mutableStateOf(1) }
   // val contexto = LocalContext.current
    val tentativa = loadVersoAtual(contexto) ?: "PSA_${capitulo.value}_${versiculo.value}"
    var chave = remember { mutableStateOf(tentativa) }
    val texto = remember { mutableStateOf(getVersoPorReferencia(chave.value)) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy (55.dp)  ,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Titulo(chave.value)
        Botoes(capitulo, versiculo, chave, texto)
        Texto(texto.value ?: "")
    }
}



@Composable
fun Texto(textoCalculado: String) {
    Text(
        text = textoCalculado,
        style = TextStyle(
            fontSize = 24.sp
        ),
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun Botoes(
    capitulo: androidx.compose.runtime.MutableState<Int>,
    versiculo: androidx.compose.runtime.MutableState<Int>,
    chave: androidx.compose.runtime.MutableState<String>,
    texto: androidx.compose.runtime.MutableState<String?>,
    modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    Row(
        modifier = modifier
            .height(60.dp)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround

    ) {

        Button(
            onClick = {
                versiculo.value-=5;
                chave.value = "PSA_${capitulo.value}_${versiculo.value}"
                val tt = getVersoPorReferencia(chave.value)
                if (tt?.isEmpty() != true) {
                    texto.value = tt
                } else {
                    capitulo.value-=1
                    if (capitulo.value < 1) {
                        capitulo.value = 1
                        versiculo.value = 1
                    } else {
                        val passos = getNumeroVersosPorCapitulo(capitulo.value) + versiculo.value
                        if (passos < 1) {
                            versiculo.value = getNumeroVersosPorCapitulo(capitulo.value)
                        } else {
                            versiculo.value = passos
                        }
                    }
                    chave.value = "PSA_${capitulo.value}_${versiculo.value}"

                    saveVersoAtual(contexto, capitulo.value, versiculo.value)
                    texto.value = getVersoPorReferencia(chave.value)
                }
            },
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
            onClick = {
                versiculo.value--;
                chave.value = "PSA_${capitulo.value}_${versiculo.value}"
                val tt = getVersoPorReferencia(chave.value)
                if (tt?.isEmpty() != true) {
                    texto.value = tt
                } else {
                    capitulo.value-=1
                    if (capitulo.value < 1) {
                        capitulo.value = 1
                        versiculo.value = 1
                    } else {
                        versiculo.value = getNumeroVersosPorCapitulo(capitulo.value)
                    }
                    chave.value = "PSA_${capitulo.value}_${versiculo.value}"
                    saveVersoAtual(contexto, capitulo.value, versiculo.value)
                    texto.value = getVersoPorReferencia(chave.value)
                }
            },
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
            onClick = {
                versiculo.value++;
                chave.value = "PSA_${capitulo.value}_${versiculo.value}"
                val tt = getVersoPorReferencia(chave.value)
                if (tt?.isEmpty() != true) {
                    texto.value = tt
                } else {
                    capitulo.value++
                    if (capitulo.value > 150) {
                        capitulo.value = 150
                        versiculo.value = getNumeroVersosPorCapitulo(capitulo.value)
                    } else {
                        versiculo.value = 1
                    }
                    chave.value = "PSA_${capitulo.value}_${versiculo.value}"
                    saveVersoAtual(contexto, capitulo.value, versiculo.value)
                    texto.value = getVersoPorReferencia(chave.value)
                }
             },
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
            onClick = {
                versiculo.value+=5;
                chave.value = "PSA_${capitulo.value}_${versiculo.value}"
                val tt = getVersoPorReferencia(chave.value)
                if (tt?.isEmpty() != true) {
                    texto.value = tt
                } else {
                    capitulo.value++
                    if (capitulo.value > 150) {
                        capitulo.value = 150
                        versiculo.value = getNumeroVersosPorCapitulo(capitulo.value)
                    } else {
                        val passos =  versiculo.value - getNumeroVersosPorCapitulo(capitulo.value-1)
                        if (passos < 1) {
                            versiculo.value = 1
                        } else {
                            versiculo.value = passos
                        }
                    }
                    chave.value = "PSA_${capitulo.value}_${versiculo.value}"
                    saveVersoAtual(contexto, capitulo.value, versiculo.value)
                    texto.value = getVersoPorReferencia(chave.value)
                }
            },
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
fun Titulo(chave: String, modifier: Modifier = Modifier) {
    val partes = chave.split("_")
    val capitulo = partes.getOrNull(1) ?: "1"
    val versiculo = partes.getOrNull(2) ?: "1"
    Text(
        "Salmos Verso a Verso",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            color = Color.Magenta
        )
    )
    Text(chave,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    )
    Row (
        modifier = modifier
            .height(60.dp)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text("Cap: $capitulo",
            style = TextStyle(
                fontSize = 24.sp
            ))
        Spacer(modifier = Modifier.width(60.dp))
        Text("Verso: $versiculo",
            style = TextStyle(
                fontSize = 24.sp
            ))
    }
}



@Preview(showBackground = true)
@Composable
fun VersoaVersoPreview() {
    val contexto = LocalContext.current
    TelaPrincipal(contexto = contexto)
}
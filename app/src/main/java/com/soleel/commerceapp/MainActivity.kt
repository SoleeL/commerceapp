package com.soleel.commerceapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.soleel.commerceapp.core.model.intentsale.IntentSaleRequestExternal
import com.soleel.commerceapp.core.model.intentsale.IntentSaleResultExternal
import com.soleel.commerceapp.core.model.intentsale.IntentSaleResultInternal
import com.soleel.commerceapp.core.model.intentsale.toInternal
import com.soleel.commerceapp.core.ui.theme.CommerceappTheme
import kotlinx.serialization.encodeToString
import androidx.core.net.toUri
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {

    private lateinit var sendIntentLauncher: ActivityResultLauncher<Intent>
    private var intentSaleResult by mutableStateOf<IntentSaleResultInternal?>(null)

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sendIntentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            this::onActivityResult
        )


        enableEdgeToEdge()
        setContent {
            CommerceappTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Comercio App",
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                    content = { paddingValues ->
                        IntentRequestFormScreen(
                            paddingValues = paddingValues,
                            onSendRequest = ::sendIntentToOtherApp,
                            intentSaleResultInternal = intentSaleResult,
                            onDismissResult = { intentSaleResult = null }
                        )
                    }
                )
            }
        }
    }

    private fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val resultJson = result.data?.getStringExtra("result")
            if (resultJson != null) {
                val intentSaleResultExternal: IntentSaleResultExternal =
                    Json.decodeFromString<IntentSaleResultExternal>(resultJson)
                intentSaleResult = intentSaleResultExternal.toInternal()
            }
        }
    }

    private fun sendIntentToOtherApp(
        commerceId: String,
        totalAmount: Int,
        paymentMethod: Int,
        cashChange: Int,
        creditInstalments: Int,
        debitChange: Int
    ) {

        val deepLinkUri = buildString {
            append("paymentapp://process_sale?")
            append("commerceId=${Uri.encode(commerceId)}&")
            append("totalAmount=$totalAmount&")
            append("paymentMethod=$paymentMethod&")
            append("cashChange=$cashChange&")
            append("creditInstalments=$creditInstalments&")
            append("debitChange=$debitChange")
        }

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = deepLinkUri.toUri()
            if (BuildConfig.DEBUG) {
                setPackage("com.soleel.paymentapp.debug")
            } else {
                setPackage("com.soleel.paymentapp")
            }
        }

        if (intent.resolveActivity(packageManager) != null) {
            sendIntentLauncher.launch(intent)
        } else {
            Toast.makeText(this, "No se encontró app destino", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun IntentRequestFormScreen(
    paddingValues: PaddingValues,
    onSendRequest: (
        commerceId: String,
        totalAmount: Int,
        paymentMethod: Int,
        cashChange: Int,
        creditInstalments: Int,
        debitChange: Int
    ) -> Unit,
    intentSaleResultInternal: IntentSaleResultInternal?,
    onDismissResult: () -> Unit
) {
    if (intentSaleResultInternal != null) {
        AlertDialog(
            onDismissRequest = onDismissResult,
            confirmButton = {
                TextButton(onClick = onDismissResult) {
                    Text("OK")
                }
            },
            title = { Text("Resultado de la venta") },
            text = {
                Column(
                    content = {
                        Text("ID: ${intentSaleResultInternal.saleId}")
                        Text("STATUS: ${intentSaleResultInternal.status.displayName}")
                        Text("MESSAGE: ${intentSaleResultInternal.message}")
                        Text("ERROR CODE: ${intentSaleResultInternal.errorCode}")
                    }
                )
            }
        )
    }

    var commerceId by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("-1") }
    var cashChange by remember { mutableStateOf("-1") }
    var creditInstalments by remember { mutableStateOf("-1") }
    var debitChange by remember { mutableStateOf("-1") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = commerceId,
            onValueChange = { commerceId = it },
            label = { Text("Id de Comercio") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            maxLines = 1
        )
        OutlinedTextField(
            value = totalAmount,
            onValueChange = { totalAmount = it },
            label = { Text("Total a pagar") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        OutlinedTextField(
            value = paymentMethod,
            onValueChange = { paymentMethod = it },
            label = { Text("Metodo de pago") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        OutlinedTextField(
            value = cashChange,
            onValueChange = { cashChange = it },
            label = { Text("Vuelto para efectivo") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        OutlinedTextField(
            value = creditInstalments,
            onValueChange = { creditInstalments = it },
            label = { Text("Cantidad de cuotas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )
        OutlinedTextField(
            value = debitChange,
            onValueChange = { debitChange = it },
            label = { Text("Vuelto con debito") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                try {
                    val request = IntentSaleRequestExternal(
                        commerceId = commerceId,
                        totalAmount = totalAmount.toIntOrNull() ?: 0,
                        paymentMethod = paymentMethod.toIntOrNull() ?: -1,
                        cashChange = cashChange.toIntOrNull() ?: -1,
                        creditInstalments = creditInstalments.toIntOrNull() ?: -1,
                        debitChange = debitChange.toIntOrNull() ?: -1
                    )
                    onSendRequest(
                        commerceId,
                        totalAmount.toIntOrNull() ?: 0,
                        paymentMethod.toIntOrNull() ?: -1,
                        cashChange.toIntOrNull() ?: -1,
                        creditInstalments.toIntOrNull() ?: -1,
                        debitChange.toIntOrNull() ?: -1
                    )
                } catch (e: Exception) {
                    Log.e("IntentForm", "Error al crear request: ${e.message}")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar Intent")
        }
    }
}
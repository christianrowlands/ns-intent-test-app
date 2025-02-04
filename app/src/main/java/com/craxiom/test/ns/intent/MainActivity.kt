package com.craxiom.test.ns.intent

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.craxiom.test.ns.intent.ui.theme.NSIntentTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NSIntentTestAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(
                            name = "Welcome",
                            modifier = Modifier.padding(innerPadding)
                        )
                        Button(onClick = {
                            val startNetworkSurveyIntent =
                                Intent("com.craxiom.networksurvey.START_SURVEY")

                            // These flags are for file logging
                            startNetworkSurveyIntent.putExtra("cellular_file_logging", true)
                            startNetworkSurveyIntent.putExtra("wifi_file_logging", true)

                            // MQTT is configured via a JSON string
                            val mqttConfigJsonString = "{\"mqtt_username\": \"auser\", " +
                                    "\"mqtt_password\": \"apassword\", " +
                                    "\"mqtt_host\": \"172.22.50.234\", " +
                                    "\"mqtt_port\": 1883, " +
                                    "\"mqtt_client\": \"aclient\", " +
                                    "\"mqtt_tls\": false, " +
                                    "\"cellular_stream_enabled\": true, " +
                                    "\"wifi_stream_enabled\": true, " +
                                    "\"device_status_stream_enabled\": true " +
                                    "}"
                            startNetworkSurveyIntent.putExtra(
                                "mqtt_config_json",
                                mqttConfigJsonString
                            )

                            startNetworkSurveyIntent.component = ComponentName(
                                "com.craxiom.networksurvey",
                                "com.craxiom.networksurvey.services.NetworkSurveyService"
                            )
                            startForegroundService(startNetworkSurveyIntent)

                            Log.i("MainActivity", "Sent the start survey intent to the service")
                        }) {
                            Text("Send Start Service Intent")
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(onClick = {
                            val stopNetworkSurveyIntent =
                                Intent("com.craxiom.networksurvey.STOP_SURVEY")

                            stopNetworkSurveyIntent.component = ComponentName(
                                "com.craxiom.networksurvey",
                                "com.craxiom.networksurvey.services.NetworkSurveyService"
                            )
                            startForegroundService(stopNetworkSurveyIntent)

                            Log.i("MainActivity", "Sent the stop survey intent")
                        }) {
                            Text("Send Stop Service Intent")
                        }

                        /*Spacer(modifier = Modifier.height(24.dp))

                        Button(onClick = {
                            val i = Intent("com.mendhak.gpslogger.TASKER_COMMAND")
                            i.setPackage("com.mendhak.gpslogger")
                            i.putExtra("immediatestart", true)
                            sendBroadcast(i)

                            Log.i("MainActivity", "Sent the start survey intent for GPSLogger")
                        }) {
                            Text("Send Start Intent Broadcast to GSPLogger")
                        }*/
                    }
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
    NSIntentTestAppTheme {
        Greeting("Android")
    }
}
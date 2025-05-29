package com.remziakgoz.cardlingua.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.remziakgoz.cardlingua.R

@Composable
fun LanguageSelector(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Image(
            painter = painterResource(
                id = if (currentLanguage == "tr") R.drawable.flag_of_turkey else R.drawable.flag_en
            ),
            contentDescription = "Language selector",
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.flag_en),
                            contentDescription = "English",
                            modifier = Modifier.size(24.dp)
                        )
                        Text("English")
                    }
                },
                onClick = {
                    onLanguageSelected("en")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { 
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.flag_of_turkey),
                            contentDescription = "Turkish",
                            modifier = Modifier.size(24.dp)
                        )
                        Text("Türkçe")
                    }
                },
                onClick = {
                    onLanguageSelected("tr")
                    expanded = false
                }
            )
        }
    }
} 
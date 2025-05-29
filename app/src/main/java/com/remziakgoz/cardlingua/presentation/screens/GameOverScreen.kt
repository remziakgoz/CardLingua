package com.remziakgoz.cardlingua.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.remziakgoz.cardlingua.domain.model.Word
import com.remziakgoz.cardlingua.presentation.viewmodels.TranslationViewModel


@Composable
fun GameOverScreen(
    score: Int,
    accuracy: Float,
    correctWords: List<Word>,
    incorrectWords: List<Word>,
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit,
    viewModel: TranslationViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = viewModel.getString("game_over"),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = viewModel.getString("score") + ": $score",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = viewModel.getString("accuracy") + ": ${(accuracy * 100).toInt()}%",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp)
        ) {
            item {
                Text(
                    text = viewModel.getString("correct_answers"),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            items(correctWords) { word ->
                WordCard(
                    word = word,
                    backgroundColor = Color(0xFF1B5E20).copy(alpha = 0.2f)
                )
            }

            item {
                Text(
                    text = viewModel.getString("incorrect_answers"),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFE53935),
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }
            items(incorrectWords) { word ->
                WordCard(
                    word = word,
                    backgroundColor = Color(0xFFB71C1C).copy(alpha = 0.2f)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onPlayAgain,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = viewModel.getString("play_again"))
            }
            Button(
                onClick = onBackToMenu,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = viewModel.getString("back_to_menu"))
            }
        }
    }
}

@Composable
private fun WordCard(word: Word, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = word.english,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = word.turkish,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
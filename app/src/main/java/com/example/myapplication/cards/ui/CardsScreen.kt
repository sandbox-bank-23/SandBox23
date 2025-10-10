package com.example.myapplication.cards.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R


@Preview
@Composable
fun CardsScreen(
    //viewModel: CardsViewModel = koinViewModel<CardsViewModel>()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
    ) {
        CardItem2()
        ExtendedFloatingActionButton(
            modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp),
            onClick = {  },
            icon = { Image(painterResource(R.drawable.ic_card_debit),
                "Extended floating action button.") },
            text = { Text(text = "Оформить дебетовую карту") },
        )
        ExtendedFloatingActionButton(
            modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp),
            onClick = {  },
            icon = { Image(painterResource(R.drawable.ic_card_credit),
                "Extended floating action button.") },
            text = { Text(text = "Оформить кредитную карту") },
        )
    }
}

@Preview
@Composable
fun CardItem() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(205.dp)
            .background(
                color = Color(0XFFE0D9FF),
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 224.dp, y = -60.dp)
                .background(
                    color = Color(0xFFFFD7B6),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = -130.dp, y = 25.dp)
                .background(
                    color = Color(0xFFD2F1E4),
                    shape = CircleShape
                )
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(0.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(22.dp, 22.dp),
                color = Color(0XFF222222),
                textAlign = TextAlign.Start,
                text = "SandboxBank",
                maxLines = 1,
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(22.dp, 22.dp),
                    color = Color(0XFF222222),
                    textAlign = TextAlign.Start,
                    text = "1 000 000 \u20BD",
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Preview
@Composable
fun CardItem2() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .height(265.dp)
            .width(367.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.card), // ваша картинка
            contentDescription = "Card background",
            modifier = Modifier.fillMaxSize().padding(),
            contentScale = ContentScale.Crop // Обрезает картинку под размер
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(22.dp, 22.dp),
                color = Color(0XFF222222),
                textAlign = TextAlign.Start,
                text = "SandboxBank",
                maxLines = 1,
                style = MaterialTheme.typography.headlineMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(22.dp, 22.dp),
                    color = Color(0XFF222222),
                    textAlign = TextAlign.Start,
                    text = "1 000 000 \u20BD",
                    maxLines = 1,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}
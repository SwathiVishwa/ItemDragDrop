package com.app.itemdragdrop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ShowList() {
    val list = ArrayList<ListData>()
    list.add(
        ListData("Row", 1)
    )
    list.add(ListData("Column", 2))
    list.add(ListData("Box", 3))
    list.add(ListData("Divider", 4))
    list.add(ListData("Text", 5))
    list.add(ListData("Card", 6))
    list.add(ListData("LazyColumn", 7))
    list.add(
        ListData("LazyRow", 8)
    )


    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(content = {
            items(list.size) {
                Card(
                    backgroundColor = Color.Gray,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()

                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(list[it].title, modifier = Modifier.align(Alignment.Start))
                        Text(list[it].position.toString(), modifier = Modifier.align(Alignment.End))

                    }
                }
            }
        })
    }
}
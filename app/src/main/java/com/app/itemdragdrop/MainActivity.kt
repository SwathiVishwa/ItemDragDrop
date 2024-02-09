package com.app.itemdragdrop

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.app.itemdragdrop.ui.theme.ItemDragDropTheme
import dagger.hilt.android.AndroidEntryPoint
import sh.calvin.reorderable.ReorderableColumn

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ItemDragDropTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SHowReorderColumn()
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Composable
    fun SHowReorderColumn() {
        val view = LocalView.current

        var list by remember { mutableStateOf(getList()) }
        var draggableItem = ""
        ReorderableColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            list = list,
            onSettle = { fromIndex, toIndex ->

                list = list.toMutableList().apply {
                    add(toIndex, removeAt(fromIndex))
                } as ArrayList<ListData>
                Log.d("OnSettle", list.toString())
                for (i in 0..<list.size) {
                    list[i].position = i + 1
                    if (list[i].title == draggableItem) {
                        Log.d("Dragged", list[i].toString())
                    }
                }
            },
            onMove = {
                view.performHapticFeedback(HapticFeedbackConstants.SEGMENT_FREQUENT_TICK)

            },
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) { _, item, isDragging ->
            key(item) {
                val elevation by animateDpAsState(
                    if (isDragging) 4.dp else 0.dp,
                    label = "elevation"
                )

                Surface(shadowElevation = elevation) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .background(Color.Gray)
                    ) {
                        Text(item.title, Modifier.padding(horizontal = 8.dp))
                        IconButton(
                            modifier = Modifier.draggableHandle(
                                onDragStarted = {
                                    Log.d("List", list.toString())
                                    view.performHapticFeedback(HapticFeedbackConstants.DRAG_START)
                                    Log.d("MOVE Item", list.toString())
                                },
                                onDragStopped = {
                                    view.performHapticFeedback(HapticFeedbackConstants.GESTURE_END)
                                    draggableItem = item.title

                                },
                            ),
                            onClick = {

                            },
                        ) {
                            Icon(Icons.Rounded.List, contentDescription = "Reorder")
                        }
                    }
                }
            }
        }
    }

    private fun getList(): ArrayList<ListData> {
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
        return list
    }
}
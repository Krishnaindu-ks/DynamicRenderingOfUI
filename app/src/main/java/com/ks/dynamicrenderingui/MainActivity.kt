package com.ks.dynamicrenderingui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.google.gson.Gson
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ks.dynamicrenderingui.ui.theme.DynamicRenderingUITheme

data class Question(
    val questionTitle: String,
    val questionType: String,
    val questionOptional: Boolean
)
class MainActivity : ComponentActivity() {

   private val jsonString = """[
    {
        "questionTitle": "Please provide a rating",
        "questionType": "RATING",
        "questionOptional": true
    },
    {
        "questionTitle": "Describe your feedback",
        "questionType": "EDITABLE",
        "questionOptional": true
    }
]"""
   private val gson = Gson()
   private val questionList = gson.fromJson(jsonString, Array<Question>::class.java).toList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DynamicRenderingUITheme {
                QuestionList(questionList = questionList)
            }
        }
    }
}
@Composable
fun QuestionItem(question:Question) {

    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(vertical = 0.dp, horizontal = 10.dp)
    ) {
        Column {

            Text(text = question.questionTitle, fontSize = 25.sp)

            when (question.questionType) {
                "RATING" -> {
                    val rating = remember { mutableStateOf(0) }
                    Row {
                        repeat(5) { index ->
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (index < rating.value) Color.Green else Color.Gray,
                                modifier = Modifier.clickable {
                                    rating.value = index + 1
                                }
                            )
                        }
                    }
                }

                "EDITABLE" -> {
                    var text by remember { mutableStateOf("") }
                    TextField(
                        value = text,
                        onValueChange = { newText ->
                            text = newText
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp)
                    )
                }
            }
        }
    }
        }

@Composable
fun QuestionList(modifier: Modifier = Modifier,questionList: List<Question>) {
    LazyColumn(modifier= modifier.padding(vertical = 30.dp)) {
        items(questionList) { question ->
            QuestionItem(question = question)
        }
    }
}




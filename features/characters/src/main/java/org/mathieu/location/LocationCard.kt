package org.mathieu.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LocationCard(name: String, type: String, onClick: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(16.dp), Arrangement.spacedBy(32.dp)) {
            Text(text = "Name: $name")
            Text(text = "Type: $type")
        }
    }
}

@Preview
@Composable
fun LocationCardPreview() {
    LocationCard(name = "Earth", type = "Planet", onClick = {})
}
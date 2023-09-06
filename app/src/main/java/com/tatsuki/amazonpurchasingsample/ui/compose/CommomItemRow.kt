package com.tatsuki.amazonpurchasingsample.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CommonItemRow(
  modifier: Modifier = Modifier,
  @StringRes labelId: Int,
  text: String,
  weight: Float = 0.3f,
) {
  Row(
    modifier = modifier
  ) {
    Text(
      text = stringResource(id = labelId),
      modifier = Modifier.weight(weight),
      style = MaterialTheme.typography.labelMedium
    )
    Text(
      text = text,
      modifier = Modifier.weight(1f - weight),
      style = MaterialTheme.typography.bodyMedium
    )
  }
}

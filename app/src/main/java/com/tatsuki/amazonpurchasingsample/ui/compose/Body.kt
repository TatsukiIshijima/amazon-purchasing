package com.tatsuki.amazonpurchasingsample.ui.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Body(
  modifier: Modifier = Modifier,
  @StringRes titleResource: Int,
  column: @Composable () -> Unit,
) {
  Column(
    modifier = modifier
  ) {
    Text(
      text = stringResource(id = titleResource),
      style = MaterialTheme.typography.headlineSmall
    )
    Spacer(modifier = Modifier.height(4.dp))
    column()
  }
}
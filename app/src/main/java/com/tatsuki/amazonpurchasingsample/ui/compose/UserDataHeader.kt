package com.tatsuki.amazonpurchasingsample.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tatsuki.amazonpurchasingsample.R
import com.tatsuki.amazonpurchasingsample.ui.theme.AppStoreSDKSampleTheme

@Composable
fun UserDataHeader(
  modifier: Modifier = Modifier,
  userId: String,
  marketplace: String
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Column {
      Text(
        text = stringResource(id = R.string.user_id_label),
        style = MaterialTheme.typography.labelLarge
      )
      Text(
        text = stringResource(id = R.string.marketplace_label),
        style = MaterialTheme.typography.labelLarge
      )
    }
    Spacer(modifier = Modifier.width(24.dp))
    Column {
      Text(
        text = userId,
        style = MaterialTheme.typography.bodyMedium
      )
      Text(
        text = marketplace,
        style = MaterialTheme.typography.bodyMedium
      )
    }
  }
}

@Preview
@Composable
private fun PreviewUserDataHeader() {
  AppStoreSDKSampleTheme {
    Surface {
      UserDataHeader(
        userId = "User1",
        marketplace = "JP"
      )
    }
  }
}
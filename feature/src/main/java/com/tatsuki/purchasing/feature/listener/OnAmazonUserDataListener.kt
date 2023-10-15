package com.tatsuki.purchasing.feature.listener

import com.amazon.device.iap.model.UserDataResponse

interface OnAmazonUserDataListener {

  fun onUserData(userDataResponse: UserDataResponse?)
}
package com.tatsuki.amazon.purchasing

import com.amazon.device.iap.model.UserDataResponse

interface OnAmazonUserDataListener {

  fun onUserData(userDataResponse: UserDataResponse?)
}
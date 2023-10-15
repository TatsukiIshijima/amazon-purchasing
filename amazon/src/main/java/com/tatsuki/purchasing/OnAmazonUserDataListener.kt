package com.tatsuki.purchasing

import com.amazon.device.iap.model.UserDataResponse

interface OnAmazonUserDataListener {

  fun onUserData(userDataResponse: UserDataResponse?)
}
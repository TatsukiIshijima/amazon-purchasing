package com.tatsuki.appstoresdksample.amazon

import com.amazon.device.iap.model.UserDataResponse

interface OnAmazonUserDataListener {

  fun onUserData(userDataResponse: UserDataResponse?)
}
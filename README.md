# amazon-purchasing

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## What's this?

This library is Amazon Appstore
SDK's [in-app-purchase](https://developer.amazon.com/ja/docs/in-app-purchasing/iap-implement-iap.html)
wrapper with Coroutine.

## Add dependencies

## How to Use

### Setup

Add ResponseReceiver to the your Manifest with reference to the
documentation [here](https://developer.amazon.com/ja/docs/in-app-purchasing/iap-implement-iap.html#responsereceiver).

### getUserData

```kotlin
coroutineScope.launch {
    val userData = amazonPurchasingService.getUserData()
}
```

### getProductData

```kotlin
coroutineScope.launch {
    val productData = amazonPurchasingService.getProductData(productSkus)
}
```

### purchase

```kotlin
coroutineScope.launch {
    val purchasedReceipt = amazonPurchasingService.purchase(productSku)
}
```

### getPurchaseUpdates

```kotlin
coroutineScope.launch {
    val purchaseUpdates = amazonPurchasingService.getPurchaseUpdates(requestAll)
}
```

### notifyFulfillment

```kotlin
amazonPurchasingService.notifyFulfillment(
    receiptId = receiptId,
    fulfillmentResult = FulfillmentResult.FULFILLED,
)
```

## Sample App

### Setup
1. Download public key from Amazon Developer Console.(ref: https://developer.amazon.com/docs/appstore-sdk/integrate-appstore-sdk.html#configure-key)
2. Add debug keystore properties in local.properties.

```local.properties
storePassword=android
keyPassword=android
keyAlias=androiddebugkey
storeFile=/Users/{UserName}/.android/debug.keystore
```

3. Execute gradle sync.

### ScreenShot

| Show Products | Purchase | Show Receipts |
|:---:|:---:|:---:|
| <img src="app/screenshot/screen1.png" width="300"/> | <img src="app/screenshot/screen2.png" width="300"/> | <img src="app/screenshot/screen3.png" width="300"/> |

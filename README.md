# amazon-purchasing

[![](https://jitpack.io/v/TatsukiIshijima/amazon-purchasing.svg)](https://jitpack.io/#TatsukiIshijima/amazon-purchasing)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## What's this?

This library is Amazon Appstore
SDK's [in-app-purchase](https://developer.amazon.com/ja/docs/in-app-purchasing/iap-implement-iap.html)
wrapper with Coroutine.

## Add dependencies

1. Add it in your settings.gradle at the end of repositories.

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency

 ```
dependencies {
    implementation 'com.github.TatsukiIshijima:amazon-purchasing:$version'
}
 ```

## How to Use

### Setup

1. Download public key(AppstoreAuthenticationKey.pem) from Amazon Developer Console.(
   ref:[Configure Appstore SDK with your public key](https://developer.amazon.com/docs/appstore-sdk/integrate-appstore-sdk.html#configure-key))
2. Copy the AppstoreAuthenticationKey.pem file. Then paste it into the app/src/main/assets folder of
   your Android Studio project.
3. Add ResponseReceiver to the your Manifest with reference to the
   documentation [here](https://developer.amazon.com/ja/docs/in-app-purchasing/iap-implement-iap.html#responsereceiver)
   .

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
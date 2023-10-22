package com.tatsuki.purchasing.fake

sealed interface FakeServiceStatus {
  object Available : FakeServiceStatus
  object Unavailable : FakeServiceStatus
}
package com.github.rcd27.idgenerator

import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.Before
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest
open class BaseContractTest {

  @Before
  fun setUp() {
    RestAssuredWebTestClient.standaloneSetup(WebTestClient.bindToServer())
  }
}
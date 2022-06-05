package com.example.backendchallenge

import com.example.backendchallenge.database.Task
import com.google.gson.Gson
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@SpringBootTest
@WebAppConfiguration
class BackendChallengeApplicationTests {
  protected lateinit var mvc: MockMvc
  @Autowired
  private lateinit var webApplicationContext: WebApplicationContext

  @BeforeAll
  fun setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
  }

  @Test
  fun testFetchAllTasks() {
//    val mvcResult = mvc.perform(
//      MockMvcRequestBuilders.get("/fetch-all-tasks/oderBy=id")
//        .accept(MediaType.APPLICATION_JSON_VALUE)
//    ).andReturn()
//
//    val status = mvcResult.response.status
//    assert(200 == status)
//    val content = mvcResult.response.contentAsString
//    val taskList = Gson().fromJson(content, Array<Task>::class.java)
//    assert(taskList.isNotEmpty())
  }

}

package com.proj.newsboard

import com.proj.newsboard.model.Country
import com.proj.newsboard.service.newtwork.api.NewsApi
import com.proj.newsboard.service.newtwork.request.ApiRequestEverything
import com.proj.newsboard.service.newtwork.request.ApiRequestTop
import org.junit.Test

class ApiTest {
    @Test
    fun requestTopTest() {
        val api: Api =
            NewsApi("1510c682a5a04c4f8eb696f6394c9844")

        val request =
            ApiRequestTop(country = Country.RussianFederation)
        val l = api.getTop(request) {

        }
    }

    @Test
    fun requestEverythingTest() {
        val api: Api =
            NewsApi("1510c682a5a04c4f8eb696f6394c9844")

        val request = ApiRequestEverything(q = "putin")
        val l = api.getEverything(request) {

        }
    }
}

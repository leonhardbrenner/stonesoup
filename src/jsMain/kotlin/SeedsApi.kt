import io.ktor.client.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

import kotlinx.browser.window

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) { serializer = KotlinxSerializer() }
}

//https://medium.com/@shubhangirajagrawal/the-7-restful-routes-a8e84201f206
object SeedsApi {

    object ChoreApi: api.seeds.ChoreApi()

    object CategoryApi: api.seeds.SeedCategoryApi()

    object DetailedSeedsApi: api.seeds.DetailedSeedsApi()

    object MySeedsApi: api.seeds.MySeedsApi()

}

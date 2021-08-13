import generated.model.SeedsDto
import io.ktor.client.request.*
import models.Resources

object RegisterOrganizeApi {
    suspend fun getMySeeds(): List<Resources.MySeeds> {
        return jsonClient.get(endpoint + SeedsDto.MySeeds.path)
    }

    suspend fun getDetailedSeed(): List<SeedsDto.DetailedSeed> {
        return jsonClient.get(endpoint + SeedsDto.DetailedSeed.path)
    }

    suspend fun getCategory(): List<SeedsDto.SeedCategory> {
        return jsonClient.get(endpoint + SeedsDto.SeedCategory.path)
    }

    suspend fun getChores(): List<SeedsDto.Chore> {
        return jsonClient.get(endpoint + SeedsDto.Chore.path)
    }
}

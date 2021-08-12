package models

interface Seeds {

    class MySeeds(
        val my_seed_id: Int,
        val seed_label: String,
        val description: String,
        val germination_test: String
    )

    class SeedCategory(
        val name: String,
        val image: String,
        val link: String
    )

    class DetailedSeed(
        val name: String,
        val maturity: String?,
        val secondary_name: String?,
        val description: String?,
        val image: String?,
        val link: String?
    )

    class Chore(
        //val id: Int,
        val parentId: Int = 0,
        val childrenIds: String, //List<Int> = listOf(),
        val name: String
        //val description: String? = null,
        //val priority: Int? = null,
        //var estimateInHours: Int? = null
    )

}

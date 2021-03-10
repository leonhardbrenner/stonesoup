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

}

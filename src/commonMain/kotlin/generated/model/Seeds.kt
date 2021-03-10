package generated.model

import kotlin.Int
import kotlin.String

interface Seeds {
  interface DetailedSeed {
    val name: String

    val maturity: String?

    val secondary_name: String?

    val description: String?

    val image: String?

    val link: String?
  }

  interface MySeeds {
    val my_seed_id: Int

    val seed_label: String

    val description: String

    val germination_test: String
  }

  interface SeedCategory {
    val name: String

    val image: String

    val link: String
  }
}

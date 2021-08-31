package generated.model

import kotlin.Int
import kotlin.String

interface Seeds {
  interface Schedule {
    val id: Int

    val choreId: Int

    val workHours: String?

    val completeBy: String?
  }

  interface Chore {
    val id: Int

    val parentId: Int

    val childrenIds: String

    val name: String

    val schedule: Seeds.Schedule?
  }

  interface DetailedSeed {
    val id: Int

    val name: String

    val maturity: String?

    val secondary_name: String?

    val description: String?

    val image: String?

    val link: String?
  }

  interface MySeeds {
    val id: Int

    val seed_label: String

    val description: String

    val germination_test: String
  }

  interface SeedCategory {
    val id: Int

    val name: String

    val image: String

    val link: String
  }
}

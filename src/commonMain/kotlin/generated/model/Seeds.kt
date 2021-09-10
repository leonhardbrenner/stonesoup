package generated.model

import kotlin.Int
import kotlin.String

interface Seeds {
  interface Chore {
    val id: Int

    val parentId: Int

    val name: String
  }

  interface DetailedSeed {
    val id: Int

    val companyId: String

    val seedId: String

    val name: String

    val maturity: String?

    val secondaryName: String?

    val description: String?

    val image: String?

    val link: String?
  }

  interface MySeeds {
    val id: Int

    val companyId: String

    val seedId: String

    val description: String

    val germinationTest: String
  }

  interface Schedule {
    val id: Int

    val choreId: Int

    val workHours: String?

    val completeBy: String?
  }

  interface SeedCategory {
    val id: Int

    val name: String

    val image: String

    val link: String
  }
}

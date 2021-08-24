package models

interface Seeds {

    class MySeeds(
        val id: Int,
        val seed_label: String,
        val description: String,
        val germination_test: String
    )

    class SeedCategory(
        val id: Int,
        val name: String,
        val image: String,
        val link: String
    )

    class DetailedSeed(
        val id: Int,
        val name: String,
        val maturity: String?,
        val secondary_name: String?,
        val description: String?,
        val image: String?,
        val link: String?
    )

    class Chore(
        val id: Int,
        val parentId: Int = 0,
        val childrenIds: String, //List<Int> = listOf(),
        val name: String
        //val description: String? = null,
        //val priority: Int? = null,
        //var estimateInHours: Int? = null
    )

    class Schedule(
        val id: Int,
        val choreId: Int, //
        val workHours: String?, //When can I work on this //TODO: This should support workDays as well.
        val completeBy: String? //When must it be done.

    )
    //TODO - move away from reflection toward DSL
    //Class("Schedule") {
    //    Val("parentId", type = Int::class) {
    //        Doc("""Stuff""")
    //        Annotation("Bla")
    //    }
    //}
}

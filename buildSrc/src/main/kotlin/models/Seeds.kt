package models

import org.gradle.internal.impldep.com.sun.xml.bind.v2.schemagen.episode.Klass
import java.util.jar.Attributes
import kotlin.reflect.KClass

interface Seeds {
    //TODO - most of these need account ids
    //TODO - we need an accounts table
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

    //TODO: I am thinking this should just be Node. We can then tie items to the tree.
    //    This should make XQuery like queries much simpler
    //
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

}
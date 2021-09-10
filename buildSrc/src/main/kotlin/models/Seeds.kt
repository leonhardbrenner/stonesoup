package models

import org.gradle.internal.impldep.com.sun.xml.bind.v2.schemagen.episode.Klass
import java.util.jar.Attributes
import kotlin.reflect.KClass

import kotlin.Int
import kotlin.String

interface Seeds {

    class Schedule(
        val id: Int,
        val choreId: Int = 0,
        val workHours: String?,
        val completeBy: String?
    )

    class Chore( //Make this Node
        val id: Int,
        val parentId: Int = 0,
        val name: String
    )

    class DetailedSeed(
        val id: Int,
        val companyId: String,
        val seedId: String,
        val name: String,
        val maturity: String?,
        val secondaryName: String?,
        val description: String?,
        val image: String?,
        val link: String?
    )

    class MySeeds(
        val id: Int,
        val companyId: String,
        val seedId: String,
        val description: String,
        val germinationTest: String
    )

    class SeedCategory(
        val id: Int,
        val name: String,
        val image: String,
        val link: String
    )

}

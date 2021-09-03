package services

import org.junit.Test
import DatabaseFactory
import generated.model.SeedsDto
import kotlin.test.assertEquals

class SeedsServiceTests {
    //TODO - This is the result of bad factoring. SeedsDb needs to be injected.
    init {
        DatabaseFactory.init()
    }
    val seedsService = SeedsService()

    @Test
    fun testMySeeds() {
        val mySeeds = seedsService.mySeeds
        with (mySeeds[0]) {
            assertEquals("Okra", description)
            assertEquals("jss:2588G.11", seed_label)
            assertEquals("3/20", germination_test)
            assertEquals(
                SeedsDto.DetailedSeed(
                    id=0,
                    name="Clemson Spineless",
                    maturity="60 Days",
                    secondary_name="Organic Okra Seed",
                    description=null,
                    image="https://www.johnnyseeds.com/dw/image/v2/BBBW_PRD/on/demandware.static/-/Sites-jss-master/default/dw2915a7d4/images/products/vegetables/02588g_01_clemsonspineless.jpg?sw=387&cx=302&cy=0&cw=1196&ch=1196",
                    link="https://www.johnnyseeds.com/vegetables/okra/clemson-spineless-organic-okra-seed-2588G.html?cgid=okra"
                ),
                detailedSeed
            )
        }
    }

    @Test
    fun testLookupMySeed() {
        val bokChoys = seedsService.lookupMySeed("Bok Choy")
        with (bokChoys[0]) {
            assertEquals("Bok Choy", description)
            assertEquals("jss:509.53", seed_label)
            assertEquals("3/17", germination_test)
            assertEquals(
                SeedsDto.DetailedSeed(
                    id=0,
                    name = "Mei Qing Choi",
                    maturity = "45 Days",
                    secondary_name = "(F1) Green Seed",
                    description = null,
                    image = "https://www.johnnyseeds.com/dw/image/v2/BBBW_PRD/on/demandware.static/-/Sites-jss-master/default/dwdb858662/images/products/vegetables/00509_01_meiqingchoi.jpg?sw=387&cx=302&cy=0&cw=1196&ch=1196",
                    link = "https://www.johnnyseeds.com/vegetables/greens/mei-qing-choi-f1-green-seed-509.html?cgid=greens"
                ),
                detailedSeed
            )
        }
        assertEquals(4, bokChoys.size)
    }

    @Test
    fun testDetailedSeeds() {
        val detailedSeeds = seedsService.getDetailedSeeds()
        detailedSeeds
    }

    //@Test
    //fun testBasicSeeds() {
    //    val basicSeeds = seedsService.getBasicSeeds()
    //    basicSeeds
    //}

    @Test
    fun testCategories() {
        val categories = seedsService.getCategories()
        categories
    }

    //@Test
    //fun testSeedFacts() {
    //    val seedFacts = seedsService.getSeedFacts()
    //    seedFacts
    //}

}
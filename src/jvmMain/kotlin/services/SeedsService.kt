package services

import dao.SeedsDao
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject

class SeedsService @Inject constructor(val dao: SeedsDao) {

    fun getDetailedSeeds() = transaction { dao.DetailedSeeds.index() }

}

package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class ScheduleService @Inject constructor(val dao: SeedsDao) {

    fun index() = dao.Schedule.index()

    fun create(source: Seeds.Schedule) = dao.Schedule.create(source)

    fun update(source: Seeds.Schedule) = dao.Schedule.update(source)

    fun destroy(id: Int) = dao.Schedule.destroy(id)

}

package services.seeds

import dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class ScheduleService @Inject constructor(val dao: SeedsDao) {

    fun index() = dao.schedule.index()

    fun create(source: Seeds.Schedule) = dao.schedule.create(source)

    fun update(source: Seeds.Schedule) = dao.schedule.update(source)

    fun destroy(id: Int) = dao.schedule.destroy(id)

}

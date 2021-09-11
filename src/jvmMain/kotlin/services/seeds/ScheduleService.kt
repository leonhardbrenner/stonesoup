package services.seeds

import generated.dao.SeedsDao
import generated.model.Seeds
import javax.inject.Inject

class ScheduleService @Inject constructor(val scheduleDao: SeedsDao.Schedule) {

    fun index() = scheduleDao.index()

    fun create(source: Seeds.Schedule) = scheduleDao.create(source)

    fun update(source: Seeds.Schedule) = scheduleDao.update(source)

    fun destroy(id: Int) = scheduleDao.destroy(id)

}

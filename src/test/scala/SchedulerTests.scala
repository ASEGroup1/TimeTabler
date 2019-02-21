import junit.framework.TestCase
import org.joda.time.{DateTime, LocalTime}
import org.junit.Assert._
import org.junit.Test
import services.generator.eventgenerator.EventGenerator
import services.generator.roomgenerator.RoomGenerator
import services.scheduler.Scheduler
import services.scheduler.poso.{Duration, Period, Room, ScheduledClass}

class SchedulerTests extends TestCase {
  def generateSchedule(eventCount: Int, roomCount: Int): Set[ScheduledClass] = {
    Scheduler.schedule(
      RoomGenerator.generate(roomCount).map(new Room(_)),
      EventGenerator.generate(eventCount),
      Array(
        new Period(DateTime.parse("01-01-19"), new Duration(new LocalTime(8, 0), new LocalTime(20, 0))),
        new Period(DateTime.parse("02-01-19"), new Duration(new LocalTime(8, 0), new LocalTime(20, 0)))
      )).get
  }

  def testIfScheduleIncludesAllEvents = assertEquals(100, generateSchedule(100, 10).size)

  def testIfNoEventsGenerateEmptySchedule = assertEquals(Set(), generateSchedule(0, 0))

  def testIfEventsDoNotIntersect = {
    var currentEnd = -1
    var currentDay = -1

    generateSchedule(100, 10).foreach(e => {
      print(currentDay + ":" + currentEnd + " < " + e.day.calendar.getDayOfMonth + ":" + e.time.start.getHourOfDay + " < ")
      if (currentDay != e.day.calendar.getDayOfMonth) currentDay = -1
      else if (currentDay < e.day.calendar.getDayOfMonth) currentDay = e.day.calendar.getDayOfMonth

      if (currentEnd > e.time.start.getHourOfDay) fail(currentEnd + " > " + e.time.start.getHourOfDay)
      currentEnd = e.time.end.getHourOfDay
    })
  }
}
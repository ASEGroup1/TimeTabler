package controllers

import java.time.temporal.ChronoField

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.scheduler.Scheduler
import views.ErrorPage

@Singleton
class ScheduleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def generateScheduleResponse = Action {
    val schedule = Scheduler.binPackSchedule(5)

    if(schedule.isEmpty) BadRequest(ErrorPage.badRequest("Could not generate, refresh for new random parameters.")).as("text/html")
    else Ok (Json.parse(schedule.get.sortBy(e => (e.day.calendar.getDayOfYear, e.room.roomName, e.time.start.get(ChronoField.MILLI_OF_DAY))).map(_.toJson).mkString("[", ",", "]"))).as("application/json")
  }
}

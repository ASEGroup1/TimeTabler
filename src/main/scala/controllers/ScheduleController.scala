package controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import services.scheduler.Scheduler
import views.ErrorPage

@Singleton
class ScheduleController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def generateScheduleResponse = Action {
    val schedule = Scheduler.generateSchedule(100, 10)

    if(schedule.isEmpty) Ok(ErrorPage.badRequest("Could not generate, refresh for new random parameters.")).as("text/html")
    else Ok (Json.parse(schedule.get.map(_.toJson).mkString("[", ",", "]"))).as("application/json")
  }
}

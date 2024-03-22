import { Router } from "express";
import { errorController } from "../app/middlewares/errors-middleware";
import EventController from "../app/controllers/event.controller";
import { body, param } from "express-validator";
import EventRepository from "../app/models/event.model";
import { HttpException } from "../app/exceptions/exception";
import { validation_code } from "../app/constants/status-codes-constant";

export const eventRoute = Router();

eventRoute.post(
  "/event",
  body("name")
    .trim()
    .notEmpty()
    .escape()
    .toLowerCase()
    .custom(async (value) => {
      const result = await EventRepository.findEvent(value);
      if (result) {
        throw new HttpException(
          "Event already exit, would you like to update it?",
          validation_code,
          {
            data: result,
            message:
              "Event already exist, Would you like to update it instead ?",
          }
        );
      }
    }),
  body("location").trim().notEmpty().escape().toLowerCase(),
  body("date").trim().notEmpty().escape().toDate(),
  body("time").trim().notEmpty().escape().isTime({ hourFormat: "hour24" }),
  errorController(EventController.create)
);

eventRoute.get("/event/");
eventRoute.get("/event/:id");
eventRoute.put(
  "/event/:id",
  param("id")
    .trim()
    .notEmpty()
    .custom(async (value) => {
      const result = await EventRepository.findEventById(value);
      if (!result) {
        throw new HttpException(
          "Event doesnt exit, would you like to create it?",
          validation_code,
          {
            data: result,
            message:
              "Event already exist, Would you like to update it instead ?",
          }
        );
      }
    })
    .escape(),
  body("name").trim().notEmpty().escape().toLowerCase(),
  body("location").trim().notEmpty().escape().toLowerCase(),
  body("date").trim().notEmpty().escape().toDate(),
  body("time").trim().notEmpty().escape().isTime({ hourFormat: "hour24" }),
  errorController(EventController.update)
);
eventRoute.delete("/event/:id");

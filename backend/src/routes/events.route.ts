import { Router } from "express";
import { errorController } from "../app/middlewares/errors-middleware";
import EventController from "../app/controllers/event.controller";
import { body, param, query } from "express-validator";
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
    .custom(async (value) => {
      const result = await EventRepository.findEvent(value);
      if (result) {
        throw new Error("Event already exit, would you like to update it?");
      }
    }),
  body("location").trim().notEmpty().escape().toLowerCase(),
  body("date").trim().notEmpty().escape().toDate(),
  body("time").trim().notEmpty().escape().isTime({ hourFormat: "hour24" }),
  errorController(EventController.create)
);

eventRoute.get("/event/", errorController(EventController.fetch));

eventRoute.get(
  "/event/search",
  query("name").trim().escape().custom(async (value) => {
    const result = await EventRepository.findEvent(value);
    if (!result) {
      throw new Error("The Event  your are searching for doesnt exit, would you like to create it?");
    }
  }),
  errorController(EventController.searchByName)
);

eventRoute.get(
  "/event/:id",
  param("id").trim().notEmpty().escape(),
  errorController(EventController.find)
);

eventRoute.put(
  "/event/:id",
  param("id")
    .trim()
    .notEmpty()
    .custom(async (value) => {
      const result = await EventRepository.findEventById(value);
      if (!result) {
        throw new Error("Event doesnt exit, would you like to create it?");
      }
    })
    .escape(),
  body("name").trim().notEmpty().escape().toLowerCase(),
  body("location").trim().notEmpty().escape().toLowerCase(),
  body("date").trim().notEmpty().escape().toDate(),
  body("time").trim().notEmpty().escape(),
  errorController(EventController.update)
);

eventRoute.delete(
  "/event/:id",
  param("id")
    .trim()
    .notEmpty()
    .custom(async (value) => {
      const id = parseInt(value)
      const result = await EventRepository.findEventById(id);
      if (!result) {
        throw new Error("Event doesnt exit, would you like to create it?");
      }
    })
    .escape(),
  errorController(EventController.delete)
);

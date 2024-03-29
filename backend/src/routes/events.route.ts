import { Router } from "express";
import { errorController } from "../app/middlewares/errors-middleware";
import EventController from "../app/controllers/event.controller";
import { body, header, param, query } from "express-validator";
import EventRepository from "../app/models/event.model";
import { HttpException } from "../app/exceptions/exception";
import { validation_code } from "../app/constants/status-codes-constant";
import IsAdmin from "../app/middlewares/isAdmin.middlewares";

export const eventRoute = Router();


/**
 * @swagger
 * tags:
 *   - name: Events 
 *     description: Endpoints for user Events Operations
 * /api/event:
 *   post:
 *     tags:
 *       - Events
 *     summary: Create new event into system as an admin user
 *     description: This endpoint allows an admin to create a new event to the system.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               name:
 *                 type: string
 *                 description: The name of the event.
 *               location:
 *                 type: string
 *                 description: The location of the event. 
 *               date:
 *                 type: string
 *                 description: The date of the event. 
 *               time:
 *                 type: string
 *                 description: The time of the event. 
 *             required:
 *               - name
 *               - location
 *               - date
 *               - time
 *     responses:
 *       '200':
 *         description: Returns status code 200 when Event created  successfully
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Event recorded in database successfully.
 *                 details:
 *                   type: object
 *                   properties:
 *                     data:
 *                       type: object
 *                       description: data of the newly created event.
 *       '400':
 *         description: Validation failed or Bad Request
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Validation failed or Bad request
 *                 details:
 *                   type: object
 */
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
  IsAdmin.tokenValidator,
  errorController(EventController.create)
);

eventRoute.get(
  "/event/",
  header("authorization").notEmpty().withMessage("No credentials sent!"),
  errorController(EventController.fetch)
);

eventRoute.get(
  "/event/search",
  query("name")
    .trim()
    .escape()
    .custom(async (value) => {
      const result = await EventRepository.findEvent(value);
      if (!result) {
        throw new Error(
          "The Event  your are searching for doesnt exit, would you like to create it?"
        );
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
  IsAdmin.tokenValidator,
  errorController(EventController.update)
);

eventRoute.delete(
  "/event/:id",
  param("id")
    .trim()
    .notEmpty()
    .custom(async (value) => {
      const id = parseInt(value);
      const result = await EventRepository.findEventById(id);
      if (!result) {
        throw new Error("Event doesnt exit, would you like to create it?");
      }
    })
    .escape(),
  IsAdmin.tokenValidator,
  errorController(EventController.delete)
);

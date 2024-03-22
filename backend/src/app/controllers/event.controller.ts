import { Request, Response } from "express";
import EventRepository from "../models/event.model";
import {
  success_code,
  unprocessableEntity,
} from "../constants/status-codes-constant";
import { JsonOutput } from "../middlewares/response.middleware";
import { matchedData, validationResult } from "express-validator";
import { HttpException } from "../exceptions/exception";

export default class EventController {
  constructor() {}

  //create new event
  static async create(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(
        "Invalid Data entered",
        unprocessableEntity,
        result.array()
      );
    }
    const data = matchedData(request);
    const event = new EventRepository(
      data.name,
      data.location,
      data.date,
      data.time
    );
    const newEvent = await event.add();
    return response.status(success_code).json(new JsonOutput(newEvent));
  }

  //update an event
  static async update(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(
        "Invalid Data entered",
        unprocessableEntity,
        result.array()
      );
    }
    const data = matchedData(request);
    const event = new EventRepository(
      data.name,
      data.location,
      data.date,
      data.time
    );
    const updatedEvent = await event.update(data.id);
    return response.status(success_code).json(new JsonOutput(updatedEvent));
  }

  static async find(request: Request, response: Response) {
    const data = <{ params: string }>request.params;
    const event = await EventRepository.findEvent(data.params);
    return response.status(success_code).json(new JsonOutput(event));
  }
}

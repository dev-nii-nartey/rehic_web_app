import { Request, Response } from "express";
import EventRepository from "../models/event.model";
import {
  resource_code,
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
      throw new HttpException(unprocessableEntity, result.array());
    }
    const data = matchedData(request);
    const event = new EventRepository(
      data.name,
      data.location,
      data.date,
      data.time
    );
    const newEvent = await event.add();
    return response.status(resource_code).json(new JsonOutput(newEvent));
  }

  //update an event
  static async update(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(unprocessableEntity, result.array());
    }
    const data = matchedData(request);
    const event = new EventRepository(
      data.name,
      data.location,
      data.date,
      data.time
    );
    const updatedEvent = await event.update(data.id);
    return response.status(resource_code).json(new JsonOutput(updatedEvent));
  }

  static async searchByName(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(unprocessableEntity, result.array());
    }
    const data = <{ name: "string" }>matchedData(request);
    const event = await EventRepository.findEvent(data.name);
    return response.status(success_code).json(new JsonOutput(event));
  }

  static async find(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(unprocessableEntity, result.array());
    }
    const data = matchedData(request);
    const event = await EventRepository.findEventById(+data.params);
    return response.status(success_code).json(new JsonOutput(event));
  }

  static async fetch(request: Request, response: Response) {
    const allEvents = await EventRepository.fetchAll();
    const count = await EventRepository.count();
    const result = { allEvents, count };

    return response.status(success_code).json(new JsonOutput(result));
  }

  static async delete(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(unprocessableEntity, result.array());
    }
    const data = matchedData(request);
    const id = parseInt(data.id);
    const deletedEvent = await EventRepository.destroy(id);
    return response.status(resource_code).json(new JsonOutput(deletedEvent));
  }
}

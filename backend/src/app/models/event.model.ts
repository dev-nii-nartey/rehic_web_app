import { Request, response, NextFunction } from "express";
import Database from "../config/db.config";
import { Prisma } from "@prisma/client";

export default class EventRepository {
  constructor(
    private eventName: string,
    private location: string,
    private date: string,
    private time: string
  ) {}

  async add() {
    const prisma = Database.open();
    try {
      const result = await prisma.event.create({
        data: {
          name: this.eventName,
          location: this.location,
          date: this.date,
          time: this.time,
        },
      });
      Database.close();
      return result;
    } catch (error) {
      new Error("Ops, something went wrong, failed to save new event");
    }
  }

  async update(id: number) {
    try {
      const prisma = Database.open();
      const result = prisma.event.update({
        where: {
          id: id,
        },
        data: {
          name: this.eventName,
          location: this.location,
          date: this.date,
          time: this.time,
        },
      });
      return result;
    } catch (error) {
      new Error("Ops, something went wrong, failed to update the  event");
    }
  }

  static async fetchAll() {
    try {
      const prisma = Database.open();
      const result = prisma.event.findMany();
      Database.close();
      return result;
    } catch (error) {
      new Error("Ops, something went wrong, failed to get all events");
    }
  }

  static async findEvent(name: string) {
    try {
      const prisma = Database.open();
      const result = prisma.event.findUniqueOrThrow({
        where: {
          name: name,
        },
      });
      Database.close();
      return result;
    } catch (error) {
      new Error("Ops, something went wrong, failed to the event");
    }
  }

  static async findEventById(id: number) {
    try {
      const prisma = Database.open();
      const result = prisma.event.findFirst({
        where: {
          id: id,
        },
      });
      Database.close();
      return result;
    } catch (error) {
      new Error("Ops, something went wrong, failed to the event");
    }
  }
}

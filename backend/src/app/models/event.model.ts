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
    try {
      const prisma = Database.open();
      const result = await prisma.event.create({
        data: {
          name: this.eventName,
          location: this.location,
          date: this.date,
          time: this.time,
        },
      });
      await Database.close();
      return result;
    } catch (error) {
      throw new Error("Ops, something went wrong, failed to save new event");
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
      throw new Error("Ops, something went wrong, failed to update the  event");
    }
  }

  static async fetchAll() {
    try {
      const prisma = Database.open();
      const result = prisma.event.findMany();
      await Database.close();
      return result;
    } catch (error) {
      throw new Error("Ops, something went wrong, failed to get all events");
    }
  }

  static async findEvent(name: string) {
    try {
      const prisma = Database.open();
      const result = await prisma.event.findFirst({
        where: {
          name: name,
        },
      });
      await Database.close();
      return result;
    } catch (error) {
      throw new Error("Ops, something went wrong, failed to  find the event by name");
    }
  }

  static async findEventById(id: number) {
    try {
      const prisma = Database.open();
      const result = await prisma.event.findFirst({
        where: {
          id: id,
        },
      });
      await Database.close();
      return result;
    } catch (error) {
      throw new Error("Ops, something went wrong, failed to find the event by id");
    }
  }

  static async destroy(id: number) {
    try {
      const prisma = Database.open();
      const result = await prisma.event.delete({
        where: {
          id: id,
        },
      });
      await Database.close();
      return result;
    } catch (error) {
      throw new Error("Ops, something went wrong, failed to delete the event");
    }
  }

  static async count() {
    try {
      const prisma = Database.open();
      const result = prisma.event.count();
      await Database.close();
      return result;
    } catch (error) {
      throw new Error(
        "Ops, something went wrong, failed to count the total number of events"
      );
    }
  }
}

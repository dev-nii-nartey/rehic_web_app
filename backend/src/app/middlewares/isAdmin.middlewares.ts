import { NextFunction, Request, Response } from "express";
import UserRepository from "../models/user.model";
import {
  unauthorized,
  unprocessableEntity,
} from "../constants/status-codes-constant";
import { JsonOutput } from "./response.middleware";
import { matchedData, validationResult } from "express-validator";
import { HttpException } from "../exceptions/exception";
import { verifyAccessToken } from "./jwt.middleware";
import { Role } from "@prisma/client";

export default class IsAdmin {
  constructor() {}
  static async tokenValidator(
    request: Request,
    response: Response,
    next: NextFunction
  ) {
    try {
      const result = validationResult(request);
      if (!result.isEmpty()) {
        throw new HttpException(unprocessableEntity, result.array());
      }
      const { authorization } = matchedData(request);
      let token = authorization.split(" ")[1];
      const data = <{ user: string }>await verifyAccessToken(token);
      const { role } = await UserRepository.findByUniqueKey(data.user);
      if (role !== Role.ADMIN) {
        throw new Error("You are not authoriazed for this operation");
      }
      next();
    } catch (error) {
      return response.status(unauthorized).json(new JsonOutput(error));
    }
  }
}

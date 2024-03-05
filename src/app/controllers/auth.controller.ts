import { Request, Response, NextFunction } from "express";
import UserRepository from "../models/user.model";
import {
  unprocessableEntity,
  success_code,
  internalServerError,
  badRequest,
  validation_code,
} from "../constants/status-codes-constant";
import { Role } from "@prisma/client";
import { validationResult } from "express-validator";
import { HttpException } from "../exceptions/exception";

export default class AuthController {
  constructor() { }
  static async register(
    request: Request,
    response: Response,
    next: NextFunction
  ) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(
        "Invalid Data entered",
        unprocessableEntity,
        result.array()
      );
    }
    const { name, email, password } = request.body;
    const userId = await UserRepository.customUserId();
    const userPayload = new UserRepository(
      name,
      email,
      password,
      userId,
      Role.CUSTOMER
    );
    const newUser = await userPayload.store();
    return response.status(success_code).json({
      message: "User created successfully, use details provided to sign in",
      details: { id: newUser?.id, name: newUser?.name },
    });
  }
  static async login(request: Request, response: Response) { 
    
  }
}

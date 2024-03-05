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
import { compare, compareSync } from "bcryptjs";
import {
  generateAccessToken,
  generateRefreshToken,
} from "../middlewares/jwt.middleware";

export default class AuthController {
  constructor() {}
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
  //Login
  static async login(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(
        "Invalid Data entered",
        unprocessableEntity,
        result.array()
      );
    }
    const { email, password } = request.body;
    const user = await UserRepository.findByUniqueKey(email);
    const pass = await compare(password, user!.password);
    if (!pass) {
      throw new HttpException("Invalid password", badRequest, [
        {
          msg: "The password the user entered is incorrect",
        },
      ]);
    }
    const accessToken = await generateAccessToken({ user: user?.id });
    const refreshToken = await generateRefreshToken({ user: user?.email });
    return response
      .status(success_code)
      .json({
        message: "User credentials passed, and is able to log in successfully",
        details: { accessToken, refreshToken },
      });
  }
}

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
import { matchedData, validationResult } from "express-validator";
import { HttpException } from "../exceptions/exception";
import { compare, compareSync, hashSync } from "bcryptjs";
import {
  generateAccessToken,
  generateRefreshToken,
} from "../middlewares/jwt.middleware";
import { JsonOutput } from "../middlewares/response.middleware";

export default class AuthController {
  constructor() {}
  static async register(
    request: Request,
    response: Response,
    next: NextFunction
  ) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(unprocessableEntity, result.array());
    }
    const { name, email, password, phoneNumber, address } =
      matchedData(request);
    const userAddress = address || null;
    const userId = await UserRepository.customUserId();
    const hashPassword = hashSync(password, 10);
    const userPayload = new UserRepository(
      name,
      email,
      hashPassword,
      userId,
      phoneNumber,
      Role.CUSTOMER,
      userAddress
    );
    const newUser = await userPayload.store();
    const responseData = {
      message: `User account created successfully`,
      details: { newUser },
    };
    return response.status(success_code).json(new JsonOutput(responseData));
  }
  //Login
  static async login(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(unprocessableEntity, result.array());
    }
    const { email, password } = matchedData(request);
    const existingUser = await UserRepository.findByUniqueKey(email);
    const pass = await compare(password, existingUser.password);
    if (!pass) {
      throw new Error("The password the user entered is incorrect");
    }
    const user = await UserRepository.findByUniqueKey(email);
    const accessToken = await generateAccessToken({ user: user?.id });
    const refreshToken = await generateRefreshToken({ user: user?.email });
    const responseData = {
      message: "User credentials passed, and is able to log in successfully",
      details: { accessToken, refreshToken },
    };
    return response.status(success_code).json(new JsonOutput(responseData));
  }

  static async updateProfile(request: Request, response: Response) {
    const result = validationResult(request);
    if (!result.isEmpty()) {
      throw new HttpException(unprocessableEntity, result.array());
    }
    const { name, email, password, phoneNumber, address, id,role } =
      matchedData(request);
      const updateUser = new UserRepository(name,email,password,id,phoneNumber,role)
  }
}

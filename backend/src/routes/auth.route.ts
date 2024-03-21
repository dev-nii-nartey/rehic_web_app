import { Router } from "express";
import { body } from "express-validator";
import AuthController from "../app/controllers/auth.controller";
import UserRepository from "../app/models/user.model";
import { HttpException } from "../app/exceptions/exception";
import { badRequest } from "../app/constants/status-codes-constant";
import { errorController } from "../app/middlewares/errors-middleware";

export const authRoute: Router = Router();

/**
 * @swagger
 * tags:
 *   - name: Authentication
 *     description: Endpoints for user authentication
 * /api/register:
 *   post:
 *     tags:
 *       - Authentication
 *     summary: Register a new user
 *     description: This endpoint allows a new user to register.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               email:
 *                 type: string
 *                 format: email
 *                 description: The email of the user.
 *               name:
 *                 type: string
 *                 description: The name of the user.
 *               password:
 *                 type: string
 *                 description: The password of the user. Must be between 5 and 10 characters.
 *             required:
 *               - email
 *               - name
 *               - password
 *     responses:
 *       '200':
 *         description: User registered successfully
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: User created successfully, use any of the user-details provided to sign in
 *                 details:
 *                   type: object
 *                   properties:
 *                     id:
 *                       type: integer
 *                       format: int64
 *                       description: The ID of the new user.
 *                     name:
 *                       type: string
 *                       description: The name of the new user.
 *       '400':
 *         description: Validation failed or user already exists
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: Validation failed or user already exists
 *                 details:
 *                   type: object
 */

//REGISTER
authRoute.post(
  "/register",
  body("email")
    .notEmpty()
    .trim()
    .isEmail()
    .toLowerCase()
    .escape()
    .custom(async (value: string) => {
      //find if user exist already
      const existingUser = await UserRepository.findByUniqueKey(value);
      if (existingUser) {
        throw new HttpException("User already exists in system", badRequest, {
          validation:
            "The email the user is trying to sign up with is already registered",
        });
      }
    }),
  body("phoneNumber").notEmpty().escape().trim(),
  body("name").notEmpty().trim().toLowerCase().escape(),
  body("password").notEmpty().isLength({ min: 5 }).isLength({ max: 10 }),
  body("address").trim().escape().toLowerCase(),
  errorController(AuthController.register)
);

/**
 * @swagger
 * tags:
 *   - name: Authentication
 *     description: Endpoints for user authentication
 * /api/login:
 *   post:
 *     tags:
 *       - Authentication
 *     summary: Login into system as a new user
 *     description: This endpoint allows a new user to login to the system.
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               email:
 *                 type: string
 *                 format: email
 *                 description: The email of the user.
 *               password:
 *                 type: string
 *                 description: The password of the user. Must be between 5 and 10 characters.
 *             required:
 *               - email
 *               - password
 *     responses:
 *       '200':
 *         description: User registered successfully
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 message:
 *                   type: string
 *                   example: User logged in successfully.
 *                 details:
 *                   type: object
 *                   properties:
 *                     acessToken:
 *                       type: string
 *                       description: access token generated for the new user.
 *                     refreshToken:
 *                       type: string
 *                       description: The refreshToken of the new user.
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
//LOGIN
authRoute.post(
  "/auth/login",
  body("email")
    .trim()
    .isEmail()
    .notEmpty()
    .escape()
    .custom(async (value: string) => {
      //find if user account exist
      const existingUser = await UserRepository.findByUniqueKey(value);
      if (!existingUser) {
        throw new HttpException(
          "User doesn't exists in  the system",
          badRequest,
          {
            validation:
              "The email the user is trying to sign in with doesnt exist in the system",
          }
        );
      }
    }),
  body("password")
    .notEmpty()
    .trim()
    .escape()
    .isLength({ min: 5 })
    .isLength({ max: 10 }),
  errorController(AuthController.login)
);

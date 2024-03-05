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
 * /auth/register:
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

authRoute.post(
  "/auth/register",
  body("email")
    .notEmpty()
    .trim()
    .isEmail()
    .toLowerCase()
    .escape()
    .custom(async (value) => {
      //find if user exist already
      const existingUser = await UserRepository.findByUniqueKey(value);
      if (existingUser) {
        throw new HttpException("User already exists in system", badRequest, {
          validation:
            "The email the user is trying to sighn up with is already registered",
        });
      }
    }),
  body("name").notEmpty().trim().toLowerCase().escape(),
  body("password").notEmpty().isLength({ min: 5 }).isLength({ max: 10 }),
  errorController(AuthController.register)
);

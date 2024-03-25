import { Request, Response, request, response, NextFunction } from "express";
import { HttpException } from "../exceptions/exception";
import { internalServerError } from "../constants/status-codes-constant";
import { JsonOutput } from "./response.middleware";

//ERROR MIDDLEWARE TO CATCH UNRESOLVED ERRORS
// export const errorHandlerMiddleware = (
//   err: any,
//   request: Request,
//   response: Response
// ) => {
//   return response
//     .status(err.errorCode)
//     .json({ message: err.message, details: err.details, errorObject: err });
// };

///GENERIC ERROR CONTROLLER
export const errorController = (method: Function) => {
  return async (request: Request, response: Response, next: NextFunction) => {
    try {
      await method(request, response);
    } catch (error) {
      if (error instanceof HttpException) {
        return response.status(error.statusCode).json(new JsonOutput(error));
      }
      return response.status(internalServerError).json(new JsonOutput(error));
    }
  };
};

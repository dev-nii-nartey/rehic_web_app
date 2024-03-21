import { Request, Response, request, response, NextFunction } from "express";
import { HttpException } from "../exceptions/exception";
import { internalServerError } from "../constants/status-codes-constant";

//ERROR MIDDLEWARE TO CATCH UNRESOLVED ERRORS
export const errorHandlerMiddleware = (
  err: any,
  request: Request,
  response: Response
) => {
  return response
    .status(err.errorCode)
    .json({ message: err.message, details: err.details, errorObject: err });
};

///GENERIC ERROR CONTROLLER
export const errorController = (method: Function) => {
  return async (request: Request, response: Response, next: NextFunction) => {
    try {
      await method(request, response);
    } catch (error) {
      if (error instanceof HttpException) {
        return response
          .status(error.errorCode)
          .json({ message: error.details[0].msg, details: error.details });
      }
      return response
        .status(internalServerError)
        .json({ message: "Something went wrong", details: error });
    }
  };
};

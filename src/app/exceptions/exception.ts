export class HttpException extends Error {
  message: string;
  details: any;
  errorCode: number;

  constructor(message: string, errorCode: number,details: any) {
    super(message);
    this.message = message;
    this.details = details;
    this.errorCode = errorCode;
  }
}

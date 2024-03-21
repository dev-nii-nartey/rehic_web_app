import { badRequest } from '../constants/status-codes-constant';
import { HttpException } from './exception';

export class BadRequest extends HttpException {
  constructor(message: string, statusCode: number) {
    super(message, badRequest, null);
  }
}

import { Request, Response } from 'express';
import Database from '../config/db.config';
import { Role } from '@prisma/client';
import { hashSync } from 'bcryptjs';
import { success_code } from '../constants/status-codes-constant';

export default class UserRepository {
  constructor(
    public name: string,
    public email: string,
    public password: string,
    public id: string,
    public role: Role
  ) {}

  //STORE NEW USERS
  async store() {
    try {
      const hashPassword = hashSync(this.password, 10);
      const prisma = Database.open();
      const user = prisma.user.create({
        data: {
          email: this.email,
          name: this.name,
          password: hashPassword,
          role: this.role,
          id: this.id,
        },
      });
      Database.close();
      return user;
    } catch (error) {
      new Error('Ops something went wrong, failed to register new user');
    }
  }

  //FIND USER IN DATABASE
  static async findByUniqueKey(email: string) {
    try {
      const prisma = Database.open();
      const user = await prisma.user.findUnique({
        where: {
          email: email,
        },
      });
      Database.close();
      return user;
    } catch (error) {
      console.log(error);
    }
  }

  //Count number of users in Database
  static async count() {
    const prisma = Database.open();
    const count = await prisma.user.findMany();
    return count.length;
  }

  //Create custom Id
  static async customUserId() {
    const totalNumber = await UserRepository.count();
    const userId = `CU-${100 + totalNumber }`;
    return userId;
  }
}

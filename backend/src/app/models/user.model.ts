import Database from "../config/db.config";
import { Role } from "@prisma/client";
import { HttpException } from "../exceptions/exception";
import { notFound } from "../constants/status-codes-constant";

export default class UserRepository {
  constructor(
    public name: string,
    public email: string,
    public password: string,
    public id: string,
    public phoneNumber: string,
    public role: Role,
    public address?: string
  ) {}

  //STORE NEW USERS
  async store() {
    try {
      const prisma = Database.open();
      const user = await prisma.user.create({
        data: {
          email: this.email,
          username: this.name,
          password: this.password,
          phone_number: this.phoneNumber,
          role: this.role,
          id: this.id,
          address: this.address,
        },
        select: {
          email: true,
          username: true,
        },
      });
      await Database.close();
      return user;
    } catch (error) {
      throw new Error("Ops something went wrong, failed to register new user");
    }
  }

  static async update(email: string) {
    try {
      const prisma = Database.open();
      const user = await prisma.user.update({
        where: {
          email: email,
        },
        data: this,
      });
    } catch (error) {
      throw new Error("Ops something went wrong, failed to register new user");
    }
  }

  //FIND USER IN DATABASE
  static async findByUniqueKey(data: string) {
    try {
      const prisma = Database.open();
      const user = await prisma.user.findFirstOrThrow({
        where: {
          OR: [{ email: data }, { id: data }],
        },
      });
      await Database.close();
      return user;
    } catch (error) {
      throw new Error("Ops something went wrong, failed to find  user");
    }
  }
  static async findUser(data: string) {
    try {
      const prisma = Database.open();
      const user = await prisma.user.findFirst({
        where: {
          OR: [{ email: data }, { id: data }],
        },
      });
      await Database.close();
      return user;
    } catch (error) {
      throw new Error("Ops something went wrong, failed to find  user");
    }
  }

  //Count number of users in Database
  static async count() {
    try {
      const prisma = Database.open();
      const count = await prisma.user.findMany();
      return count.length;
    } catch (error) {
      throw new Error("Ops something went wrong, failed to count users");
    }
  }

  //Create custom Id
  static async customUserId() {
    try {
      const totalNumber = await UserRepository.count();
      const userId = `CU-${100 + totalNumber}`;
      return userId;
    } catch (error) {
      throw new Error("Ops something went wrong, failed to assign userId");
    }
  }
}

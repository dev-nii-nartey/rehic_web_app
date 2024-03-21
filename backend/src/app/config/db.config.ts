import { PrismaClient } from '@prisma/client';

export default class Database {
  private static prisma: PrismaClient | undefined;
  private constructor() {}
  static open() {
    Database.prisma = new PrismaClient();
    return Database.prisma;
  }

  static close() {
    Database.prisma?.$disconnect();
  }
}

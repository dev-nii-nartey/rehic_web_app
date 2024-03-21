import UserRepository from "../../app/models/user.model";
import { beforeEach, describe, it } from "node:test";
import Database from "../../app/config/db.config";
jest.mock("../../app/config/db.config");

describe("Methods in the UserRepository", () => {
  let userRepo: UserRepository;
  beforeEach(() => {
    userRepo = new UserRepository(
      "test",
      "email@gmail.com",
      "password",
      "id",
      "05040030203",
      "ADMIN"
    );
  });
  it("should create a user repository instance", () => {
    expect(userRepo).toBeInstanceOf(UserRepository);
  });
});

/* describe("The UserRepository", () => {
  let prisma: any;
  let repo: UserRepository;
  beforeEach(() => {
    repo = new UserRepository(
      "test",
      "email",
      "password",
      "id",
      "05040030203",
      "ADMIN"
    );
    prisma = {
      user: {
        create: jest.fn(),
        findUnique: jest.fn(),
        findMany: jest.fn(),
      },
    };
    // Mock the Database.open method to return the mocked PrismaClient
    (Database.open as jest.Mock).mockReturnValue(prisma);
  });

  it("should test the store a new user", async () => {
    await repo.store();
    expect(repo).toBeInstanceOf(UserRepository);
    // expect(prisma.user.create).toHaveBeenCalled();

    // expect(prisma.user.create).toHaveBeenCalledWith({
    //   data: {
    //     email: "email",
    //     username: "test",
    //     password: "password",
    //     phone_number: "05040030203",
    //     role: "ADMIN",
    //     id: "id",
    //   },
    // });
  });
}); */

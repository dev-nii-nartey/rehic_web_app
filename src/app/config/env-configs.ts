import dotenv from "dotenv";

dotenv.config({ path: ".env" });

export const PORT = 8080 || process.env.PORT;

export const SECRET = process.env.SECRET;

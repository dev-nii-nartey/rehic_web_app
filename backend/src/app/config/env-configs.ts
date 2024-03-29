import dotenv from "dotenv";

dotenv.config({ path: ".env" });

export const PORT = parseInt(process.env.PORT!);

export const SECRET = process.env.SECRET;

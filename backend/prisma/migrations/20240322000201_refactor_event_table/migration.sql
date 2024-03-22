/*
  Warnings:

  - You are about to drop the column `Date` on the `Event` table. All the data in the column will be lost.
  - You are about to drop the column `Location` on the `Event` table. All the data in the column will be lost.
  - You are about to drop the column `Time` on the `Event` table. All the data in the column will be lost.
  - Added the required column `date` to the `Event` table without a default value. This is not possible if the table is not empty.
  - Added the required column `location` to the `Event` table without a default value. This is not possible if the table is not empty.
  - Added the required column `time` to the `Event` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "Event" DROP COLUMN "Date",
DROP COLUMN "Location",
DROP COLUMN "Time",
ADD COLUMN     "date" TIMESTAMP(3) NOT NULL,
ADD COLUMN     "location" TEXT NOT NULL,
ADD COLUMN     "time" TIMESTAMP(3) NOT NULL;

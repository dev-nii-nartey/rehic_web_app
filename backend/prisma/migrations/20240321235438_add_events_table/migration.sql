-- DropIndex
DROP INDEX "addresses_id_key";

-- DropIndex
DROP INDEX "products_name_key";

-- DropIndex
DROP INDEX "users_id_key";

-- AlterTable
ALTER TABLE "addresses" ADD CONSTRAINT "addresses_pkey" PRIMARY KEY ("id");

-- AlterTable
ALTER TABLE "products" ADD COLUMN     "id" SERIAL NOT NULL,
ADD CONSTRAINT "products_pkey" PRIMARY KEY ("id");

-- CreateTable
CREATE TABLE "Event" (
    "id" SERIAL NOT NULL,
    "name" TEXT NOT NULL,
    "Date" TIMESTAMP(3) NOT NULL,
    "Time" TIMESTAMP(3) NOT NULL,
    "Location" TEXT NOT NULL,

    CONSTRAINT "Event_pkey" PRIMARY KEY ("id")
);

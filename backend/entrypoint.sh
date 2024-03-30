#!/bin/sh
yarn prisma generate
yarn prisma migrate dev --name container_init
yarn run dev

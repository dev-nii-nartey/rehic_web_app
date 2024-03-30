FROM node:21.7.1-alpine3.19 as development

WORKDIR /app

COPY  ./package.json .

RUN yarn install

COPY prisma ./prisma

COPY . .

EXPOSE 3000

RUN chmod +x ./entrypoint.sh

CMD  if [ "$NODE_ENV" = "development" ]; then yarn run dev; else yarn run build; fi


FROM node:21.7.1-alpine3.19 as production

WORKDIR /app

COPY --from=development ./package.json .

RUN yarn install --only-production

COPY --from=development /dist .

EXPOSE 3000

CMD [ "node","./dist/app.js" ]
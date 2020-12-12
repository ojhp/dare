FROM strapi/base:14

WORKDIR /app
COPY package.json yarn.lock /app/
RUN yarn install --production

COPY . /app
ENV NODE_ENV=production
RUN yarn build

EXPOSE 1337
CMD ["yarn", "start"]

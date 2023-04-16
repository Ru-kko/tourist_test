FROM node:lts as node

ARG HOST

WORKDIR /app
COPY . /app

RUN echo "export const environment = { HOST: '${HOST}', };" > /app/src/environments/environment.prod.ts 

RUN npm install
RUN npm run build --configuration=production

FROM nginx:alpine

EXPOSE 80

COPY --from=node /app/dist/ng-client /usr/share/nginx/html

COPY ./docker/nginx.conf /etc/nginx/conf.d/default.conf
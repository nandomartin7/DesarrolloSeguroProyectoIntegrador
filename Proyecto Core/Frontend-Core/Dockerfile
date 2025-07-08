FROM node:18-alpine
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

# El CLI de Vue sirve por defecto en el puerto 8080,
# así que lo cambiamos a 5173 para coincidir con tu mapeo.
EXPOSE 5173

CMD ["npm","run","serve","--","--host","0.0.0.0","--port","5173"]

import express from 'express';
import { PORT } from './app/config/env-configs';
import dotenv from 'dotenv';
import bodyParser from 'body-parser';
import cors from 'cors';
import morgan from 'morgan';
import { authRoute } from './routes/auth.route';
import swaggerDocs from './app/utils/swagger';
import { errorHandlerMiddleware } from './app/middlewares/errors-middleware';

const app = express();

app.use(express.json());
app.use(cors());
app.use(morgan('dev'));

app.use('/api', authRoute);

app.listen(PORT, () => {
  console.log(`app is live on http://localhost:${PORT}`);
  console.log(
    `docs are available on http://localhost:${PORT}/api/e-commerce/docs`
  );
  swaggerDocs(app, PORT);
});

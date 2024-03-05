import { Express, Request, Response, response } from 'express';
import swaggerJsdoc from 'swagger-jsdoc';
import swaggerUi from 'swagger-ui-express';
import { version } from '../../../package.json';
import { PORT } from '../config/env-configs';

const options: swaggerJsdoc.Options = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'E-commerce REST API Docs',
      version,
      description: 'A simple e-commerce API for REHIC',
    },
    servers:[
      {
        url: `http://localhost:${PORT}`
      }
    ]
  },
  apis: ['./src/routes/*.ts'],
};

const swaggerSpec = swaggerJsdoc(options);

function swaggerDocs(app: Express, port: number) {
  app.use(
    '/api/e-commerce/docs',
    swaggerUi.serve,
    swaggerUi.setup(swaggerSpec)
  );

  app.get('docs.json', (request: Request, response: Response) => {
    response.setHeader('Content-Type', 'application/json');
    response.send(swaggerSpec);
  });
}

export default swaggerDocs;

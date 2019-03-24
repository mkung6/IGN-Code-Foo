import sqlite from 'sqlite';
import { GraphQLServer, PubSub } from 'graphql-yoga';
import typeDefs from './data/typedefs';
import resolvers from './data/resolver';

const GRAPHQL_PORT = 4000;
const pubsub = new PubSub();

//run the migration file and start the server
sqlite.open(':memory:', { cached: true })
  .then(() => sqlite.migrate())
  .then(() => {
    const server = new GraphQLServer({
      typeDefs,
      resolvers,
      context: {
        db: {
          get: (...args) => sqlite.get(...args),
          all: (...args) => sqlite.all(...args),
          run: (...args) => sqlite.run(...args)
        },
        sub: {
          pubsub
        }
      }
    });
    server.start(() =>
      console.log(
        `GraphQL server is now running on http://localhost:${GRAPHQL_PORT}`
      ));
  })
  .catch(e => console.error(e));

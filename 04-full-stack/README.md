## Setup

Clone/download the repo onto a local folder.

For macOS/linux:

cd to the root folder, `04-full-stack`, and `npm install`.

cd to `/server` folder and `npm install`.

Then run the server with `npm start` in the `/server` folder.

In another terminal, start the React app with `npm start` in the root folder--`/04-full-stack/`

Enter `localhost:3000` in your browser to join the chat.

Visit `localhost:4000` in your browser to view graphiql.

## Chat app details

For my chat application, I wanted to create something relatively simple and lightweight. So for my stack I chose to use React with GraphQL, and SQLite so that we don't have to download and run any database server.

Because I want the users to get chat messages from other users in real time, I use GraphQL subscriptions--which is basically a websocket. That way we can minimize the amount of times we need to GET from the database.

I use `graphql-yoga` to simplify our package and take care of our server, subscriptions, and transport.

When a user visits our app, they first input a username. Then the chat room will display all messages, which we receive from our GraphQL query.

For our mutation, `createMessage`, we first insert the new message into our database. Then, we retrieve it back from the database so that we can get its id number and display it in the browser. The latest message will always have the highest id number, due to being a primary key, so we can just query by `MAX(id)`. We also publish this event with our subscription, so that other clients will receive the new message.

Because we were not concerned with scaling the app to more than two users, I took the liberty to simplify a few things. For example, we only have a `message` table. Otherwise, I would also implement a `user` table, with a "user has many messages" relation. However, by having two separate tables like this we can run into [N+1 problems](https://medium.com/slite/avoiding-n-1-requests-in-graphql-including-within-subscriptions-f9d7867a257d). Therefore, I keep it simple here with just one table.

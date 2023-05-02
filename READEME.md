# Chess-Demon

### Deployment

## Initial setup
To deploy the project first clone the discord bot repository found here https://github.com/Josh-Renwick-99/ChessDemonBot

Once this has been downloaded run 'npm install' to download the dependencies

Once this has been done you can start up the bot by navigating to the root directory of the bot and running 'node index.js'

You can invite the bot to a server using this link 'https://discord.com/api/oauth2/authorize?client_id=1070311942129975327&permissions=8&scope=bot'

## Database

This project only works if the database is up and running, you can create a local version of the database by navigating into the deployment directory and running the following commands. You must have docker desktop installed

The local database has no password so you can log in via just the root user

docker build -t chessdemondb .

docker run --name chessdemondb-container -p 8085:3306 -d chessdemondb

The project should now be set up, contact me via email if there are problems

### Usage



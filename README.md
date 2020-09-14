# CS-340-API
Api used by the Tweeter app that allows for database access. Uses multiple endpoints using AWS Lambda functions, as well as other backend tools such as AWS queuing and Dynamo DB.

## AWS Technologies
A list of AWS technologies and what they were used for:

#### DynamoDB
DynamoDB was used as the database system, as it allowed for rapid inserts and data retreival.

#### Simple Queueing Service
SQS was used to handle massive api calls by packaging them up into groups before inserting or retrieving them from the database. This API could handle over 10,000 requests in under 30 seconds. 

#### AWS Lambda
Lamba was used to house the api calls, written in Java. This allowed for minimal payment to AWS, and for lazy endpoint loading.

#### API Gateway
Used to store all the api calls, and contained all documentation needed for each call. This documentation included YAML files and descriptions, as well as examples for requests and responses from each api

# OAuth 1.0A Authorization Header Generator

This is a simple Maven project which accepts consumer key, consumer secret, HTTP method and the protected URL as arguments. Then it generates an Authorization header which conforms to OAuth 1.0A specifications from the supplied data.

This project has *no dependencies* and is designed to be a simple, portable and pure *Java 8* implementation of the OAuth 1.0A header specifications.

### Usage

1. Build the executable JAR.

```
mvn clean package
```

2. Specify arguments and execute.

```
java -DconsumerKey="your-consumer-key" -DconsumerSecret="YourConsumerSecret" -DhttpMethod="HTTP_METHOD" -Durl="https://protected.url/here" -jar target/oauth10a-header-generator-1.1.jar
```

That's it! The OAuth 1.0A header is generated.

This header can be directly used with any HTTP client (such as Spring's built-in REST template) to access an OAuth 1.0 protected resource by setting the header key as `Authorization` and its value as the output obtained in step 2.

### Docker

This project is available as an image on [DockerHub](https://hub.docker.com/r/gourabix/oauth-header-generator).

To run the image as a container, use the following command:

```
docker container run -e CONSUMER_KEY="your-consumer-key" -e CONSUMER_SECRET="YourConsumerSecret" -e HTTP_METHOD="POST" -e URL="https://protected.url/here" gourabix/oauth-header-generator:1.1
```

Note that the consumer key, consumer secret, HTTP method and URL are specified as environment variables in the command above. When they are not provided, default values are used instead.

### Support this project

Found an issue? Head over to the issues tab and create a new issue.

Pull requests and suggestions are welcome.

---

License: MIT

Author: [Gourab Sarkar](https://gourabsarkar.netlify.app)

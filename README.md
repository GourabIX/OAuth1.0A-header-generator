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
java -DconsumerKey="your-consumer-key" -DconsumerSecret="YourConsumerSecret" -DhttpMethod="HTTP_METHOD" -Durl="https://protected.url/here" -jar target/oauth10a-header-generator-1.0.jar
```

That's it! The OAuth 1.0A header is generated.

This header can be directly used with any HTTP client (such as Spring's built-in REST template) to access an OAuth 1.0 protected resource by setting the header key as `Authorization` and its value as the output obtained in step 2.

### Support this project

Found an issue? Head over to the issues tab and create a new issue.

Pull requests and suggestions are welcome.

---

License: MIT

Author: [Gourab Sarkar](https://gourabsarkar.netlify.app)

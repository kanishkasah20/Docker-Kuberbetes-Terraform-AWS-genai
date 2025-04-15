
# 🚀 Deploying Spring Boot Application with AWS Lambda and Integrating AWS Bedrock GenAI

This guide helps you deploy a **Spring Boot** application to **AWS Lambda** using **AWS SAM (Serverless Application Model)** and integrate **AWS Bedrock GenAI** services for advanced generative AI capabilities.

---

## 📦 Prerequisites

- AWS CLI installed and configured
- AWS account with Lambda, API Gateway, IAM, Bedrock access
- Java 17+ and Maven installed
- Docker installed (for SAM build)
- AWS SAM CLI installed ([Install SAM](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html))

---

## 📁 Project Structure

```
springboot-lambda-genai/
├── src/
│   └── main/java/com/example/lambda/
│       └── Handler.java
├── template.yaml
├── pom.xml
└── README.md
```

---

## 🧱 Step 1: Create Spring Boot Lambda Handler

Create a handler class that implements `RequestHandler` or uses `SpringBootRequestHandler`:

```java
public class Handler extends SpringBootRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {}
```

Ensure you have a controller to process API calls and connect with Bedrock.

---

## 🧰 Step 2: Setup template.yaml

Define your Lambda function and API Gateway:

```yaml
AWSTemplateFormatVersion: '2010-09-09'
Transform: AWS::Serverless-2016-10-31
Resources:
  SpringLambdaFunction:
    Type: AWS::Serverless::Function
    Properties:
      Handler: com.example.lambda.Handler
      Runtime: java17
      CodeUri: .
      MemorySize: 1024
      Timeout: 30
      Events:
        Api:
          Type: Api
          Properties:
            Path: /genai
            Method: post
```

---

## 🛠️ Step 3: Build and Deploy with SAM

```bash
sam build
sam deploy --guided
```

This will prompt for stack name, region, capabilities, etc.

---

## 🤖 Step 4: Integrate AWS Bedrock

In your service class, use AWS SDK to connect to Bedrock (e.g., Claude, Titan, Jurassic):

```java
import software.amazon.awssdk.services.bedrock.BedrockClient;

BedrockClient bedrock = BedrockClient.create();
// Use bedrock.invokeModel(...) or similar
```

- Configure IAM roles and policies to allow Lambda to invoke Bedrock
- Use a pre-defined model (like Claude) with the right permissions

---

## 🧪 Example Request

You can POST a JSON body like:

```json
{
  "prompt": "Explain the concept of serverless architecture."
}
```

API Gateway URL will be shown after `sam deploy`, access it like:

```
https://<api-id>.execute-api.<region>.amazonaws.com/genai
```

---

## 🔐 IAM Permissions Required

Attach the following policy to your Lambda execution role:

```json
{
  "Effect": "Allow",
  "Action": [
    "bedrock:InvokeModel"
  ],
  "Resource": "*"
}
```

---

## 🧹 Cleanup

To delete your Lambda stack:

```bash
sam delete
```

---

## 🧠 Notes

- You may need to configure environment variables or secret access for Bedrock
- For large prompt/response sizes, increase the Lambda memory and timeout
- Monitor via CloudWatch for logs and debugging

---

## 🔗 References

- [AWS SAM Documentation](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html)
- [AWS Bedrock Docs](https://docs.aws.amazon.com/bedrock/latest/userguide/what-is-bedrock.html)
- [Spring Boot on Lambda](https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html)


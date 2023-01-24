# Infobip application security Java education

NOTE: This is by design a vulnerable application. Primary use is to demonstrate bad coding practices in Java and to show off specific vulnerabilities that are not present in some other projects. It can be used as a demo for various devsecops methods as well. Do not use in production!


## Installation


```
cd ib-secops-appsec-edu/
mvn package spring-boot:repackage
docker build -t ib-appsec .
docker run -it -p 8000:8080 ib-appsec
```

## Currently implemented vulns

- Command injection - vulnerability with dot bypass
- Command injection - example of proper Safe API usage
- Command injection - example of wrong Safe API usage
- SQL injection - vulnerability with frontend bypass"),
- SQL injection - example of weak sanitization
- SQL injection - example of proper query usage
- Reflected XSS - POST variant
- Reflected XSS - GET variant
- File upload bad coding practice
- Server side request forgery - unrestricted
- Server side request forgery - Bypassing fixed hosts"),
- XML External Entities injection

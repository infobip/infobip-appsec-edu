# Infobip application security javascript education

NOTE: This is by design a vulnerable application. Primary use is to demonstrate bad coding practices in Javascript and to show off specific vulnerabilities that are not present in some other projects. It can be used as a demo for various devsecops methods as well. Do not use in production!


## Usage


```
cd infobip-appsec-javascript-edu/client
docker build -f Dockerfile -t client .
docker run -it -p 3000:3000 client
cd ../server
docker build -f Dockerfile -t server .
docker run -it -p 9000:9000 server
```
Additionally to ensure proper working of template injection example, place yourself in /server and fill the .env file with your email credentials and be sure to the host in server/helpers/EmailHelper.js.

After this, reach the frontend on `http://localhost:3000`.

## Currently implemented vulns

- Cross-site scripting
- Path traversal
- Template injection
- XML External Entities Injection
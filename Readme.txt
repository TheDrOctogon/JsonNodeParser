Compile SpringJsonNodeApplication.java in preferred Java Spring IDE. I used eclipse.

Tested using SoapUI rest operations to send Post and Get Requests

send post request with inbound.json to http://localhost:8080/inboundJson

send get request to http://localhost:8080/outboundJson to get converted Json

Request Mapping sends HTTP Headers 

example:
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date:Tue, 26 Nov 2019 18:34:05 GMT

Request Mapping also sends proper http responses

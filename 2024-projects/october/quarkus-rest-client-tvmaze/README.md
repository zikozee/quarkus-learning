

## STEPS TO USING REST CLIENT
- create an interface proxy (see com.zee.proxy)
  - Annotate with @RegisterRestClient
  - set the appropriate Http Verb and endpoint path
  - map the uris to the HttpVerbs
- set the base uri and scope in application.properties
- inject within the resource and mark as @RestClient

# Exception Handling

## Steps
- add dependency
```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-hibernate-validator</artifactId>
</dependency> 
```
- use @Valid to validate T data see ApiResource (e.g Customer ) 
  - this generates a ConstraintViolationException
  - or manually Validate using hibernate Validator
- throw either quarkus default exceptions or custom exceptions
- create a Provider matching the exact exception to be captured or  generic exception. (See exceptionhandler package)
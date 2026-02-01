package com.mike.healthmadeeasy.controller;

import com.mike.healthmadeeasy.exception.BadRequestException;
import com.mike.healthmadeeasy.exception.FoodNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    //////////////////////////////////
    /// PRIVATE HELPERS
    //////////////////////////////////

    private ProblemDetail baseProblem(HttpStatus status, String title, String detail, HttpServletRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setType(URI.create("about:blank"));
        problemDetail.setProperty("timestamp", Instant.now().toString());
        problemDetail.setProperty("path", request.getRequestURI());

        return problemDetail;
    }

    private static void mapAndMergeFieldErrors(MethodArgumentNotValidException exception, Map<String, List<String>> errors) {
        exception.getBindingResult().getFieldErrors().forEach(err -> {
            String field = err.getField();
            String message = err.getDefaultMessage();
            errors.merge(field, List.of(message), (oldList, newList) -> {
                var merged = new ArrayList<>(oldList);
                merged.addAll(newList);
                return merged;
            });
        });
    }

    //////////////////////////////////
    /// HANDLERS
    //////////////////////////////////

    /**
     * Handles @Valid request-body validation failures.
     *
     * Triggered when a controller method parameter annotated with @Valid fails Bean Validation
     * (e.g., invalid FoodCreateRequest fields). Returns HTTP 400 with an RFC7807 ProblemDetail body
     * and an "errors" property mapping field names to a list of validation messages.
     *
     * Example "errors":
     *  {
     *    "name": ["must not be blank"],
     *    "referenceGrams": ["must be greater than or equal to 1"]
     *  }
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException exception,
                                                          HttpServletRequest request) {

        ProblemDetail problemDetail = baseProblem(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                "Request body contains invalid fields",
                request);

        Map<String, List<String>> errors = new LinkedHashMap<>();

        mapAndMergeFieldErrors(exception, errors);

        problemDetail.setProperty("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);

    }

    /**
     * Handles malformed / unreadable JSON request bodies.
     *
     * Triggered when Spring cannot deserialize the HTTP request body into the expected @RequestBody type
     * (e.g., invalid JSON syntax, wrong field types such as a string where a number is expected, or an
     * unexpected structure). Returns HTTP 400 with an RFC7807 ProblemDetail body describing the parse failure.
     *
     * Typical causes:
     *  - Missing/extra commas or brackets in JSON
     *  - Wrong value types (e.g., "referenceGrams": "abc" instead of a number)
     *  - Invalid numeric formats for BigDecimal fields
     */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleBadJson(HttpMessageNotReadableException exception,
                                                       HttpServletRequest request) {

        ProblemDetail problemDetail = baseProblem(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON",
                "Request body could not be parsed. Check JSON syntax and field types.",
                request
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Handles request parameter / path variable type conversion failures.
     *
     * Triggered when Spring cannot convert an incoming value (e.g., a @PathVariable or @RequestParam)
     * into the expected Java type for a controller method argument (for example, "not-a-uuid" into a UUID,
     * or "abc" into an int). Returns HTTP 400 with an RFC7807 ProblemDetail body that identifies the
     * parameter name and the invalid value.
     *
     * Common examples:
     *  - GET /api/foods/not-a-uuid  (expects UUID id)
     *  - GET /api/foods?page=abc    (expects integer page)
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(MethodArgumentTypeMismatchException exception,
                                                            HttpServletRequest request) {

        String name = exception.getName();
        String value = String.valueOf(exception.getValue());

        ProblemDetail problemDetail = baseProblem(
                HttpStatus.BAD_REQUEST,
                "Invalid Parameter",
                "Parameter: " + name + " has invalid value: " + value,
                request
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Handles application/domain-level "bad request" conditions.
     *
     * Triggered when your own code explicitly rejects a request as invalid despite being syntactically
     * correct JSON (i.e., it parsed successfully and may have passed basic Bean Validation), but violates
     * a business rule or service-level invariant. Returns HTTP 400 with an RFC7807 ProblemDetail body
     * using the exception message as the detail.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> handleBadRequests(BadRequestException exception,
                                                           HttpServletRequest request) {

        ProblemDetail problemDetail = baseProblem(
          HttpStatus.BAD_REQUEST,
          "Bad Request",
                exception.getMessage(),
                request
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    /**
     * Handles missing Food resources.
     *
     * Triggered when the application cannot find a Food for a requested identifier (typically thrown
     * from the service layer when a repository lookup returns empty). Returns HTTP 404 with an RFC7807
     * ProblemDetail body using the exception message as the detail.
     *
     * Common example:
     *  - GET /api/foods/{id} where {id} does not exist in the repository
     */
    @ExceptionHandler(FoodNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleFoodNotFound(FoodNotFoundException exception,
                                                            HttpServletRequest request) {

        ProblemDetail problemDetail = baseProblem(
                HttpStatus.NOT_FOUND,
                "Food Not Found",
                exception.getMessage(),
                request
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    /**
     * Fallback handler for any unhandled exceptions.
     *
     * Triggered when an exception is thrown during request processing that is not matched by a more
     * specific @ExceptionHandler method. Returns HTTP 500 with a generic RFC7807 ProblemDetail response
     * to avoid leaking internal implementation details to clients.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception exception,
                                                          HttpServletRequest request) {

        ProblemDetail problemDetail = baseProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected Error",
                "Something went wrong",
                request
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

}

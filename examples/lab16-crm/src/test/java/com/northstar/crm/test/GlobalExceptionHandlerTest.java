import com.northstar.crm.dto.CustomerRequestDTO;
import com.northstar.crm.exception.BusinessException;
import com.northstar.crm.exception.ErrorResponse;
import com.northstar.crm.exception.GlobalExceptionHandler;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    private final Validator validator =
            Validation.buildDefaultValidatorFactory()
                    .getValidator();


    @Test
    void mapsNotFound() {
        var err = handler.fromBusiness(
                BusinessException.notFound(
                        "CUS-9999",
                        "lab-request-001"));

        assertEquals(404, err.getStatus());
        assertEquals(
                "lab-request-001",
                err.getCorrelationId());
        assertEquals(
                "CUSTOMER_NOT_FOUND",
                err.getError());
    }


    @Test
    void mapsValidationEmail() {
        CustomerRequestDTO dto = new CustomerRequestDTO();

        dto.setCustomerId("CUS-1001");
        dto.setFullName("Amina Khan");
        dto.setEmail("not-an-email");
//      dto.setPhone("555-1001");
        dto.setStatus("ACTIVE");

        Set violations = validator.validate(dto);

        var err = handler.fromValidation(
                violations,
                "lab-request-001");

        assertEquals(400, err.getStatus());
        assertEquals(
                "VALIDATION_FAILED",
                err.getError());

        assertEquals(
                "lab-request-001",
                err.getCorrelationId());

        assertTrue(
                err.getErrors().containsKey("email"));
    }


    @Test
    void mapsConflict() {
        var err = handler.fromBusiness(
                BusinessException.conflict(
                        "illegal status transition ACTIVE -> PROSPECT",
                        "lab-request-001"));

        assertEquals(409, err.getStatus());
        assertEquals(
                "BUSINESS_CONFLICT",
                err.getError());
        assertEquals(
                "lab-request-001",
                err.getCorrelationId());
    }
}
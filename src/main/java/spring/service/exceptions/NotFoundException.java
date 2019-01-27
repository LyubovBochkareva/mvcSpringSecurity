package spring.service.exceptions;

/**
 * Thrown when the user tried finding non-exist object in the DataBase.
 *
 * @author Lyubov Bochkareva
 * @see {@link spring.service.impl.RoleServiceImpl#getRoleByRoleName(String)}
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructor a NotFoundException using the given exception message.
     *
     * @param message The message explaining the reason for the exception.
     */
    public NotFoundException(String message) {
        super(message);
    }
}

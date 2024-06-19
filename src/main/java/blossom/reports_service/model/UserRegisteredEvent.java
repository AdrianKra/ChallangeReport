package blossom.reports_service.model;

/**
 * This class represents an event that is triggered when a user is registered.
 * 
 * @param id    The id of the user.
 * @param name  The name of the user.
 * @param email The email of the user.
 */
public record UserRegisteredEvent(Integer id, String name, String email) {
}
package ch.heigvd.gen.interfaces;

/**
 * Strings used in the composition of our HTTP requests
 */
public interface IRequests {

    String BASE_URL = "http://loki.cpfk.net:9999/api/";
    String LOGIN = "auth";
    String REGISTER = "register";
    String EVENTS = "events";
    String GET_ALL_USERS = "users";
    String GET_ALL_UNKNOWN_USERS = "users/unknowns";
    String GET_USER = "users/";
    String GET_SELF = "users/self";
    String SET_MESSAGES_READ = "/read";

    String GET_CONTACTS = "contacts";
    String GET_CONTACT = "contacts/";

    String GET_GROUPS = "groups";
    String GET_GROUP = "groups/";
    String GET_MEMBERS = "/members";
    String GET_MEMBER = "/members/";

    String GET_MESSAGES = "/messages";

}

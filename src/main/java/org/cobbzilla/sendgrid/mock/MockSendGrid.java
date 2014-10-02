package org.cobbzilla.sendgrid.mock;

import org.cobbzilla.sendgrid.SendGrid;
import org.cobbzilla.sendgrid.SendGridUser;
import org.cobbzilla.util.http.ApiConnectionInfo;

import java.util.HashMap;
import java.util.Map;

public class MockSendGrid extends SendGrid {

    private Map<String, SendGridUser> users = new HashMap<>();

    @Override public SendGridUser findUser(String username) throws Exception {
        return users.get(username);
    }

    @Override public ApiConnectionInfo addUser(SendGridUser user) throws Exception {
        users.put(user.getUsername(), user);
        return getApiConnectionInfo(user);
    }

    @Override public ApiConnectionInfo addOrEditUser(SendGridUser user) throws Exception { return addUser(user); }

    @Override public void editUser(SendGridUser user) throws Exception { addUser(user); }

    @Override public void deleteUser(String username) throws Exception { users.remove(username); }
}

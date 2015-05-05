package org.cobbzilla.sendgrid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.cobbzilla.util.json.JsonUtil;
import org.cobbzilla.util.http.ApiConnectionInfo;
import org.cobbzilla.wizard.api.NotFoundException;
import org.cobbzilla.wizard.util.RestResponse;

import java.util.HashMap;
import java.util.Map;

import static org.cobbzilla.util.daemon.ZillaRuntime.die;
import static org.cobbzilla.util.string.StringUtil.urlParameterize;

@Accessors(chain=true)  @NoArgsConstructor
@Slf4j @JsonIgnoreProperties(ignoreUnknown=true)
public class SendGrid {

    private static final String PARAM_API_USER = "api_user";
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_USERNAME = "username";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_PERMISSIONS = "permissions";

    public static final String SMTP_RELAY = "smtp.sendgrid.net";
    public static final int SMTP_RELAY_PORT = 587;

    public static final String BASE_URI = "https://api.sendgrid.com/api/";

    @Getter @Setter private ApiConnectionInfo credentials;

    @Getter(value = AccessLevel.PROTECTED, lazy=true) private final SendGridApiClient apiClient = initApiClient();

    public SendGrid (ApiConnectionInfo credentials) {
        this.credentials = credentials;
    }

    private SendGridApiClient initApiClient() {
        return new SendGridApiClient(BASE_URI);
    }

    private Map<String, String> initParams() {
        final Map<String, String> params = new HashMap<>();
        params.put(PARAM_API_USER, credentials.getUser());
        params.put(PARAM_API_KEY, credentials.getPassword());
        return params;
    }

    public SendGridUser findUser(String username) throws Exception {
        final Map<String, String> params = initParams();
        params.put(PARAM_USERNAME, username);
        try {
            final RestResponse response = getApiClient().get("credentials/get.json?" + urlParameterize(params));
            final SendGridJsonUser[] users = JsonUtil.fromJson(response.json, SendGridJsonUser[].class);
            return users.length == 0 ? null : new SendGridUser(users[0]);

        } catch (NotFoundException e) {
            return null;
        }
    }

    public ApiConnectionInfo addUser (SendGridUser user) throws Exception {

        final Map<String, String> params = initParams();
        params.put(PARAM_USERNAME, user.getUsername());
        params.put(PARAM_PASSWORD, user.getPassword());
        params.put(PARAM_PERMISSIONS, user.getPermissions().toString());

        final RestResponse response = getApiClient().post("credentials/add.json", params);
        if (!response.json.contains("success")) {
            die("Error adding user: " + response.json);
        }
        return getApiConnectionInfo(user);
    }

    protected ApiConnectionInfo getApiConnectionInfo(SendGridUser user) {
        final ApiConnectionInfo info = new ApiConnectionInfo();
        info.setUser(user.getUsername());
        info.setPassword(user.getPassword());
        return info;
    }

    public ApiConnectionInfo addOrEditUser (SendGridUser user) throws Exception {
        SendGridUser found = findUser(user.getUsername());
        if (found == null) {
            return addUser(user);
        } else {
            editUser(user);
            return null;
        }
    }

    public void editUser (SendGridUser user) throws Exception {

        final Map<String, String> params = initParams();
        params.put(PARAM_USERNAME, user.getUsername());
        if (user.hasPassword()) params.put(PARAM_PASSWORD, user.getPassword());
        params.put(PARAM_PERMISSIONS, user.getPermissions().toString());

        final RestResponse response = getApiClient().post("credentials/edit.json", params);
        if (!response.json.contains("success")) {
            die("Error editing user: "+response.json);
        }
    }

    public void deleteUser (String username) throws Exception {
        Map<String, String> params = initParams();
        params.put(PARAM_USERNAME, username);
        final RestResponse response = getApiClient().post("credentials/remove.json", params);
        if (!response.json.contains("success")) {
            die("Error adding user: "+response.json);
        }
    }

}
